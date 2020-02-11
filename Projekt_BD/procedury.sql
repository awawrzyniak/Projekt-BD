DELIMITER $$
create function stan(ilosc int)
returns varchar(20)
deterministic
begin
declare stan varchar(20);
if (ilosc<5) then
set stan='pilnie potrzebna';
elseif(ilosc<10) then
set stan='stan średni';
elseif(ilosc<20) then
set stan='stan optymalny';
elseif(ilosc>100) then
set stan='brak potrzeb';
end if;
return stan;
end $$
DELIMITER ; 


delimiter $$
create trigger aktualizowanie_plus
before insert
on krew for each row
begin
declare id_bank int default (select new.bank_id);
declare naz varchar(45) default (select nazwa from bank where new.bank_id=id);
declare grupa varchar(3) default (select new.grupa_krwi);
declare ile int default (select count(grupa_krwi) from stan_magazyn where bank_id=id_bank and grupa_krwi=grupa);
declare st varchar(20) default (select stan(ile+1));
declare ist int default(select distinct id from magazyn where bank_id=new.bank_id and grupa_krwi=new.grupa_krwi);
 if(isnull(ist)) then
insert into magazyn(id,bank_id,nazwa,grupa_krwi,ilosc,stan) value(id,new.bank_id,naz,new.grupa_krwi,1,'pilnie potrzebna');
 else
update magazyn set ilosc=ile+1 where bank_id=id_bank and grupa_krwi=grupa;
update magazyn set stan=st where bank_id=id_bank and grupa_krwi=grupa;
 end if;
end $$
delimiter ;

delimiter $$
create trigger aktualizowanie_minus
before delete
on krew for each row
begin
declare id_bank int default (select old.bank_id);
declare grupa varchar(3) default (select old.grupa_krwi);
declare ile int default (select count(grupa_krwi) from stan_magazyn where bank_id=id_bank and grupa_krwi=grupa);
declare st varchar(20) default (select stan(ile-1));
update magazyn set ilosc=ile-1 where bank_id=id_bank and grupa_krwi=grupa;
update magazyn set stan=st where bank_id=id_bank and grupa_krwi=grupa;
end $$
delimiter ;

select * from magazyn;
call utylizacja(@in);
select @in;

DELIMITER $$
create procedure utylizacja(out info varchar(15))
begin
declare id_k int default(select max(id) from krew);
declare id_min int default(select min(id) from krew);
declare grupa varchar(3) default(select grupa_krwi from krew where id=id_k);
declare id_d int default(select dawca_id from krew where id=id_k);
declare data_pob date default(select data_pobrania from krew where id=id_k);
declare t int default(select datediff(current_date(),data_pob));
set info = 'Baza aktualna';
simple_loop: LOOP
if(t>42) then
set info = 'Utylizacja...';
insert into krew_archiwum(id,grupa_krwi,dawca_id,data_pobrania,data_archiwizacji,status) value (id,grupa,id_d,data_pob,current_date(),'utylizacja');
delete from krew where id=id_k;
select max(id) from krew into id_k;
select grupa_krwi from krew where id=id_k into grupa;
select dawca_id from krew where id=id_k into id_d;
select data_pobrania from krew where id=id_k into data_pob;
select datediff(current_date(),data_pob) into t;
end if;
IF id_k=id_min THEN
            LEAVE simple_loop;
         END IF;
END LOOP simple_loop;
end $$
DELIMITER ;


DELIMITER $$
create procedure pobierz(in gr varchar(3), in id_b int, in ilosc int, out info varchar(30))
begin
DECLARE a INT Default 0 ;
declare id_k int;
set info='';
simple_loop: LOOP
SET a=a+1;
select min(id) from krew where grupa_krwi=gr and bank_id=id_b into id_k;
if(not isnull(id_k)) then
call transfuzja(id_k);
set info=concat(info,'Transfuzja...');
else
set info=concat(info,'Brak krwi');
end if;
         IF a=ilosc THEN
            LEAVE simple_loop;
         END IF;
END LOOP simple_loop;
end $$
DELIMITER ; 


DELIMITER $$
create procedure transfuzja(in id_k int)
begin
declare grupa varchar(3) default(select grupa_krwi from krew where id=id_k);
declare id_d int default(select dawca_id from krew where id=id_k);
insert into krew_archiwum(id,grupa_krwi,dawca_id,status) value (id,grupa,id_d,'transfuzja');
delete from krew where id=id_k;
end $$
DELIMITER ; 

delimiter $$
create procedure dodaj_dawce(in im varchar(45), in naz varchar(45), in ur date, in tel varchar(9), in waga double, in gr varchar(3), in id_ban int,out info varchar(90))
begin
declare id_n int default(select id from dawca where imie=im and nazwisko=naz and data_urodzenia=ur and numer_telefonu=tel);
declare nowy boolean default(select isnull(id_n));
declare wiek int default (select year(from_days(datediff(current_date(),ur))));
declare id_max int default(select max(id) from dawca);
if(nowy and waga>=50 and wiek>=18) then
insert into dawca(id,imie,nazwisko,data_urodzenia,numer_telefonu)
values(id_max+1,im,naz,ur,tel);
set id_n=id_max+1;
call dodaj_krew(gr,id_n,id_ban,nowy,@i);
set info='Dodano nowego dawce i pobrano od niego jednostke krwi.';
elseif(not nowy and waga>=50) then
call dodaj_krew(gr,id_n,id_ban,nowy,@i);
set info=@i;
else
set info='Nie spelniono warunkow wieku i/lub wagi.';
end if;
end $$
delimiter ;

delimiter $$
create procedure dodaj_krew(in gr varchar(3),in id_daw int,in id_ban int,in now boolean,out info varchar(90))
begin
declare ostatnia_data1 date default (select max(data_pobrania) from krew where id_daw = dawca_id);
declare ostatnia_data2 date default (select max(data_pobrania) from krew_archiwum where id_daw = dawca_id);
declare dni1 int default (select datediff(current_date(),ostatnia_data1));
declare dni2 int default (select datediff(current_date(),ostatnia_data2));
declare grupa1 varchar(3) default (select distinct grupa_krwi from krew where id_daw = dawca_id);
declare grupa2 varchar(3) default (select distinct grupa_krwi from krew_archiwum where id_daw = dawca_id);
if(not now and not(grupa1=gr or grupa2=gr)) then
set info='Grupa krwi nie jest zgodna z danymi zarejestrowanego dawcy!';
elseif(now or (not now and ostatnia_data1>ostatnia_data2 and dni1>56) or (not now and ostatnia_data1<ostatnia_data2 and dni2>56)) then
insert into krew(id,grupa_krwi,dawca_id,bank_id,data_pobrania) values(id,gr,id_daw,id_ban,curdate());
set info='Pobrano krew od zarejestrowanego dawcy!';
else
set info='Nie dodano krwi! Nie spelniono warunkow czestosci oddawania krwi.';
end if;
end $$
delimiter ;
