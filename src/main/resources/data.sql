insert into product
values('classic','Offers the most basic level of advertisement', '269.99');

insert into product
values('std','Allows advertisers to use a company logo and use a longer presentation text', '322.99');

insert into product
values('premium','Same benefits as Standout Ad, but also puts the advertisement at the top of the results, allowing higher visibility', '394.99');

insert into deal (company, classic, std, premium)
values('secondBite','3:2','1:1','1:1');

insert into deal (company, classic, std, premium)
values('axilCoffeeRoasters','1:1','1:1','1:1');

insert into deal (company, classic, std, premium)
values('myer','1:1','5:4','1:1');

insert into discount (company, classic, std, premium)
values('secondBite',0,0,0);

insert into discount (company, classic, std, premium)
values('axilCoffeeRoasters',0,299.99,0);

insert into discount (company, classic, std, premium)
values('myer',0,0,389.99);