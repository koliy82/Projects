set ansi_nulls on
go
set ansi_padding on
go
set quoted_identifier on 
go

---<--! Find All DB !-->---
--select name from sys.databases



---<--! Create DB !-->---
IF (DB_ID('Taxi_DB') is null) CREATE DATABASE [Taxi_DB]
ELSE drop database [Taxi_DB]
CREATE DATABASE [Taxi_DB]
go

---<--! Use DB !-->---
use [Taxi_DB]
go

--EXEC sp_Tables @table_qualifier = 'Taxi_DB',
--@table_owner = 'dbo',
--@table_type = "'TABLE'";
--go

SELECT TABLE_NAME
FROM INFORMATION_SCHEMA.TABLES
WHERE TABLE_TYPE = 'BASE TABLE' AND TABLE_CATALOG='dbName'

---<--! Create Table Client !-->---
IF (OBJECT_ID('dbo.Client') IS NULL) CREATE TABLE [dbo].[Client]
(
	[ID_Client] [int] not null identity(1,1),
	[Client_LastName] [varchar] (30) not null,
	[Client_Name] [varchar] (30) not null,
	[Client_MiddleName] [varchar] (30) not null,
	[Client_Passport] [varchar] (16) not null,
	[Client_Card_Number] [varchar] (19) not null,
	[Client_Card_Date] [varchar] (5) not null,
	[Client_Card_CVV] [varchar] (3) not null,
	[Client_Login] [varchar] (32) not null,
	[Client_Password] [varchar] (32) not null,
	constraint [PK_Client] primary key clustered ([ID_Client] asc) on [PRIMARY],
	constraint [CH_Client_Passport] check (len([Client_Passport]) = 10),
	constraint [UQ_Client_Passport] unique ([Client_Passport]),
	constraint [UQ_Client_Card_Number] unique ([Client_Card_Number]),
	constraint [UQ_Client_Login] unique ([Client_Login]), 
	constraint [CH_Client_Card_Number] check ([Client_Card_Number] like ('[0-9][0-9][0-9][0-9] [0-9][0-9][0-9][0-9] [0-9][0-9][0-9][0-9] [0-9][0-9][0-9][0-9]')),
	constraint [CH_Client_Card_Date] check ([Client_Card_Date] like ('[0-9][0-9]/[0-9][0-9]')),
	constraint [CH_Client_Card_CVV] check ([Client_Card_CVV] like ('[0-9][0-9][0-9]')),
	constraint [CH_Client_Login] check (len([Client_Login]) >= 8),
	constraint [CH_Client_Password_Upper] check ([Client_Password] like ('%[A-Z]%')),
	constraint [CH_Client_Password_Lower] check ([Client_Password] like ('%[a-z]%')),
	constraint [CH_Client_Password_Simvols] check ([Client_Password] like ('%[!@#$%^&*()]%'))
)
go

---<--! Insert Table Client !-->---
insert into [dbo].[Client] ([Client_LastName],[Client_Name],[Client_MiddleName],[Client_Passport],[Client_Card_Number],[Client_Card_Date],[Client_Card_CVV],[Client_Login],[Client_Password])
values ('Романов','Роман','Романович','3914785578','4958 0661 3589 7660','07/24','766','Romanovvv','Pa$$w0rd'),
('Семёнов','Леонард','Романович','4964635078','4248 0871 3129 7120','02/26','799','Semenovvv','ADHND@&*YRNAP'),
('Орехов','Май','Тимурович','4944435388','4225 0899 3339 7170','09/25','799','Orehovvv','AIDJMWIOuavw(*PU@)(U'),
('Керимбаев','Максим','Антонович','4083135981','4444 0171 3559 7420','09/23','479','Kerimbaevvv','daIMJCD*AU@*M)MF'),
('Романова','Варвара','Васильевна','4143332188','4211 0494 3119 7220','09/29','299','Romanovvva','CAU()MD)CA@DAi')
go

---<--! Select Table Client !-->---
select [Client_LastName]+' '+[Client_Name]+' '+[Client_MiddleName] as "ФИО клиента", [Client_Login] as "Логин клиента", [Client_Password] as "Пароль клиента"  from [dbo].[Client]
order by [Client_LastName] ASC
go

select [ID_Client],[Client_LastName] as 'Фамилия',[Client_Name] as 'Имя',[Client_MiddleName] as "Отчество", [Client_Login] as "Логин", [Client_Password] as "Пароль", [Client_Passport] as 'Паспорт', [Client_Card_Number] as 'Номер карты',[Client_Card_Date] as 'Срок карты',[Client_Card_CVV] as 'CVV'  from [dbo].[Client]
go

--update [dbo].[Client] set [Client_LastName]='{}', [Client_Name]='{}',[Client_MiddleName]='{}',[Client_Login]='{}',[Client_Password]='{}',[Client_Passport]='{}',[Client_Card_Number]='{}',[Client_Card_Date]='{}',[Client_Card_CVV]='{}'
--where [ID_Client]='{}'

---<--! Create Table Category !-->---
IF (OBJECT_ID('dbo.Category') IS NULL) CREATE TABLE [dbo].[Category]
(
	[ID_Category] [int] not null identity(1,1),
	[Category_Name] [varchar] (32) not null,
	[Category_Nacenka] [int] not null,
	constraint [PK_Category] primary key clustered ([ID_Category] asc) on [PRIMARY],
	constraint [UQ_Category_Name] unique ([Category_Name])
)
go

---<--! Insert Table Category !-->---
insert into [dbo].[Category] ([Category_Name],[Category_Nacenka])
values ('Эконом','0'),('Комфорт','10'),('Бизнес','20')
go

---<--! Select Table Category !-->---
select [Category_Name] as "Категория", [Category_Nacenka] as "Наценка" from [dbo].[Category]
order by [Category_Nacenka] ASC
go

---<--! Create Table TManager !-->---
IF (OBJECT_ID('dbo.TManager') IS NULL) CREATE TABLE [dbo].[TManager]
(
	[ID_TManager] [int] not null identity(1,1),
	[TManager_LastName] [varchar] (30) not null,
	[TManager_Name] [varchar] (30) not null,
	[TManager_MiddleName] [varchar] (30) not null,
	[TManager_Login] [varchar] (32) not null,
	[TManager_Password] [varchar] (32) not null,
	constraint [PK_TManager] primary key clustered ([ID_TManager] asc) on [PRIMARY],
	constraint [CH_TManager_Login] check (len([TManager_Login]) >= 8),
	constraint [CH_TManager_Password_Upper] check ([TManager_Password] like ('%[A-Z]%')),
	constraint [CH_TManager_Password_Lower] check ([TManager_Password] like ('%[a-z]%')),
	constraint [CH_TManager_Password_Simvols] check ([TManager_Password] like ('%[!@#$%^&*()]%'))
)
go

---<--! Insert Table TManager !-->---
insert into [dbo].[TManager] ([TManager_LastName],[TManager_Name],[TManager_MiddleName],[TManager_Login],[TManager_Password])
values ('Павлов','Павел','Павлович','Pavlovvv','uahwopAn(*H@@(F0@pafmwi'),
('Воробьёв','Венедикт','Дамирович','Vorobevvv','jdAHAwn2f@@28)F(*A;m'),
('Панфилов','Наум','Тихонович','Panfilovvv','DAdwoidAj2D##MU@()'),
('Шашков','Осип','Мартынович','Shachkovvv','oadmAAwdP)(@@@Mdua0'),
('Туров','Василий','Андреевич','Turovvvv','damjFAwd(@@@(m9d')
go

---<--! Select Table TManager !-->---
select [TManager_LastName]+' '+[TManager_Name]+' '+[TManager_MiddleName] as "ФИО менеджера по уч.т.", [TManager_Login] as "Логин", [TManager_Password] as "Пароль" from [dbo].[TManager]
order by [TManager_LastName] ASC
go

--update [dbo].[TManager] set [TManager_LastName] = '{TManagerLastName_tb.Text}',[TManager_Name] = '{TManagerName_tb.Text}',[TManager_MiddleName] = '{TManagerMiddleName_tb.Text}',[TManager_Login] = '{TManagerLogin_tb.Text}',[TManager_Password] = '{TManagerPass_tb.Text}' where [ID_TManager] = '{selectedID}'
--go

---<--! Create Table Firm !-->---
IF (OBJECT_ID('dbo.Firm') IS NULL) CREATE TABLE [dbo].[Firm]
(
	[ID_Firm] [int] not null identity(1,1),
	[Firm_Name] [varchar] (128) not null,
	constraint [PK_Firm] primary key clustered ([ID_Firm] asc) on [PRIMARY],
	constraint [UQ_Firm_Name] unique ([Firm_Name])
)
go

---<--! Insert Table Firm !-->---
insert into [dbo].[Firm] ([Firm_Name])
values ('ООО "Федеральное такси"'),('ООО "Яндекс.такси"'),('НПАО "Куда скажете туда и поедем", НПАО "КСТиП"')
go

---<--! Select Table Firm !-->---
select [Firm_Name] as "Фирма" from [dbo].[Firm]
order by [Firm_Name] ASC
go

---<--! Create Table Color !-->---
IF (OBJECT_ID('dbo.Color') IS NULL) CREATE TABLE [dbo].[Color]
(
	[ID_Color] [int] not null identity(1,1),
	[Color_Name] [varchar] (32) not null,
	constraint [PK_Color] primary key clustered ([ID_Color] asc) on [PRIMARY],
	constraint [UQ_Color_Name] unique ([Color_Name])
)
go

---<--! Insert Table Color !-->---
insert into [dbo].[Color] ([Color_Name])
values ('Вишневый'),('Чёрный'),('Пурпурно-оранжевый'),('Красный'),('Зелёный')
go

---<--! Select Table Color !-->---
select [Color_Name] as "Цвет" from [dbo].[Color]
--order by [Color_Name] ASC
go

---<--! Create Table Body_Type !-->---
IF (OBJECT_ID('dbo.Body_Type') IS NULL) CREATE TABLE [dbo].[Body_Type]
(
	[ID_Body_Type] [int] not null identity(1,1),
	[Body_Type_Name] [varchar] (16) not null,
	constraint [PK_Body_Type] primary key clustered ([ID_Body_Type] asc) on [PRIMARY],
	constraint [UQ_Body_Type_Name] unique ([Body_Type_Name])
)
go

---<--! Insert Table Body_Type !-->---
insert into [dbo].[Body_Type] ([Body_Type_Name])
values ('5‑дв. Хетчбэк'),('4‑дв. Хетчбэк')
go

---<--! Select Table Body_Type !-->---
select [Body_Type_Name] as "Тип корпуса" from [dbo].[Body_Type]
order by [Body_Type_Name] ASC
go


---<--! Create Table Feature !-->---
IF (OBJECT_ID('dbo.Feature') IS NULL) CREATE TABLE [dbo].[Feature]
(
	[ID_Feature] [int] not null identity(1,1),
	[Feature_Name] [varchar] (32) not null,
	constraint [PK_Feature] primary key clustered ([ID_Feature] asc) on [PRIMARY],
	constraint [UQ_Feature_Name] unique ([Feature_Name])
)
go

---<--! Insert Table Feature !-->---
insert into [dbo].[Feature] ([Feature_Name])
values ('Нет'),('Детское сиденье'),('Люк на крыше'),('Большой багаж'),('Можно с животным')
go

---<--! Select Table Feature !-->---
select [Feature_Name] as "Дополнительная характеристика" from [dbo].[Feature]
order by [Feature_Name] ASC
go

---<--! Create Table Driver !-->---
IF (OBJECT_ID('dbo.Driver') IS NULL) CREATE TABLE [dbo].[Driver]
(
	[ID_Driver] [int] not null identity(1,1),
	[Driver_LastName] [varchar] (32) not null,
	[Driver_Name] [varchar] (32) not null,
	[Driver_MiddleName] [varchar] (32) not null,
	[Driver_Login] [varchar] (30) not null,
	[Driver_Password] [varchar] (30) not null,
	[Driver_Exp] [int] not null,
	[Driver_BirthDate] [Date] not null,
	[Driver_SendDate] [Date] not null default(getDate()),
	[Driver_EndDate] [Date] not null,
	[Driver_FromSend] [varchar] (max) not null,
	[Driver_Number] [varchar] (10) not null,
	[Driver_Residence] [varchar] (max) not null,
	[Driver_Category] [varchar] (1) not null,
	constraint [PK_Driver] primary key clustered ([ID_Driver] asc) on [PRIMARY],
	constraint [CH_Driver_Login] check (len([Driver_Login]) >= 8),
	constraint [CH_Driver_Password_Upper] check ([Driver_Password] like ('%[A-Z]%')),
	constraint [CH_Driver_Password_Lower] check ([Driver_Password] like ('%[a-z]%')),
	constraint [CH_Driver_Password_Simvols] check ([Driver_Password] like ('%[!@#$%^&*()]%')),
	constraint [CH_Driver_BirthDate] check ([Driver_BirthDate] <= getDate()),
	constraint [CH_Driver_EndDate] check ([Driver_EndDate] >= getDate()),
	constraint [CH_Driver_Number] unique ([Driver_Number])
)
go

---<--! Insert Table Driver !-->---
insert into [dbo].[Driver] ([Driver_LastName],[Driver_Name],[Driver_MiddleName],[Driver_Login],[Driver_Password],[Driver_Exp],[Driver_BirthDate],[Driver_SendDate],[Driver_EndDate],[Driver_FromSend],[Driver_Number],[Driver_Residence],[Driver_Category])
values ('Иванов','Иван','Иванович','Ivanovvv','Pa$$w0rd','5','20.04.1978','04.10.2017','04.10.2027','ГИБДД 7724','7705384100','Г.Москва','B'),
('Рожков','Кондрат','Улебович','Rozhkovvv','Pa$$w0rd','5','10.05.1998','08.11.2018','08.11.2028','ГИБДД 7814','7703334100','Г.Москва','B'),
('Бирюков','Илларион','Дмитрьевич','Birukovvv','Pa$$w0rd','5','20.07.1985','04.10.2015','04.10.2025','ГИБДД 7224','7704344100','Г.Москва','B')
go

---<--! Select Table Driver !-->---
select [Driver_LastName]+' '+[Driver_Name]+' '+[Driver_MiddleName] as "ФИО водителя", [Driver_Login] as "Логин", [Driver_Password] as "Пароль", [Driver_Exp] as "Стаж (лет)", [Driver_BirthDate] as "Дата рождения",[Driver_SendDate] as "Дата выдачи", [Driver_EndDate] as "Дата окончания", [Driver_FromSend] as "Кем выдан",[Driver_Number] as "Серия и номер",[Driver_Residence] as "Место жительства", [Driver_Category] as "Категория" from [dbo].[Driver]
order by [Driver_LastName] ASC
go

select [ID_Driver], [Driver_LastName] as 'Фамилия',[Driver_Name] as 'Имя', [Driver_MiddleName] as 'Отчество', [Driver_Login] as 'Логин', [Driver_Password] as 'Пароль', [Driver_Exp] as 'Стаж (лет)', [Driver_BirthDate] as 'Дата рождения',[Driver_SendDate] as 'Дата выдачи', [Driver_EndDate] as 'Дата окончания', [Driver_FromSend] as 'Кем выдан',[Driver_Number] as 'Серия и номер',[Driver_Residence] as 'Место жительства', [Driver_Category] as 'Категория' from [dbo].[Driver]
go

--update [dbo].[Driver] set [Driver_LastName] = '{DriverLastName_tb.Text}',[Driver_Name] = '{DriverName_tb.Text}',[Driver_MiddleName] = '{DriverMiddleName_tb.Text}',[Driver_Login] = '{DriverLogin_tb.Text}',[Driver_Password] = '{DriverPass_tb.Text}',[Driver_Exp] = '{DriverExp_tb.Text}',[Driver_BirthDate] = '{DriverBirthDate_tb.Text}',[Driver_SendDate] = '{DriverStartDate_tb.Text}',[Driver_EndDate] = '{DriverEndDate_tb.Text}',[Driver_FromSend] = '{Driverkv_tb.Text}',[Driver_Number] = '{DriverNumber_tb.Text}',[Driver_Residence] = '{DriverMestoShitelstva_tb.Text}',[Driver_Category] = '{DriverCategory_tb.Text}'
--go

---<--! Create Table Order !-->---
IF (OBJECT_ID('dbo.Order') IS NULL) CREATE TABLE [dbo].[Order]
(
	[ID_Order] [int] not null identity(1,1),
	[Order_Date] [Date] not null default(getDate()),
	[Order_Time] [Time] not null default(getDate()),
	[Order_Number] [int] not null,
	[Order_Status] [varchar] (10) not null,
	[Category_ID] [int] not null,
	[Client_ID] [int] not null,
	[Driver_ID] [int] not null,
	constraint [PK_Order] primary key clustered ([ID_Order] asc) on [PRIMARY],
	constraint [CH_Order_Status] check ([Order_Status] = 'В пути' OR [Order_Status] = 'Закрыт'),
	constraint [FK_Category_ID] foreign key ([Category_ID]) references [dbo].[Category] ([ID_Category]),
	constraint [FK_Client_ID] foreign key ([Client_ID]) references [dbo].[Client] ([ID_Client]),
	constraint [FK_Driver_ID] foreign key ([Driver_ID]) references [dbo].[Driver] ([ID_Driver])
)
go


---<--! Insert Table Order !-->---
insert into [dbo].[Order] ([Order_Date],[Order_Time],[Order_Number],[Order_Status],[Category_ID],[Client_ID],[Driver_ID])
values ('20.08.2021','15:05:00','1','В пути','2','1','1'),
('21.09.2021','14:02:30','2','В пути','1','2','2'),
('10.12.2021','11:05:00','3','В пути','2','3','3'),
('10.12.2021','11:10:01','4','Закрыт','1','3','2'),
('14.12.2021','11:09:05','5','Закрыт','1','3','1')
go

--update [dbo].[Order] set [Order_Date] = '', [Order_Time] = '', [Order_Number] = '', [Order_Status] = '', [Category_ID] = '', [Client_ID] = '', [Driver_ID] = '' where [ID_Order] = ''
--go

select * from [dbo].[Order]
go

update [dbo].[Order] set [Order_Status] = 'Закрыт', [Driver_ID] = 2 where [ID_Order] = '1'
go

--delete from [dbo].[Order]
--where [ID_Order] = 1
--go

---<--! Select Max Order Number !-->---
--SELECT MAX(Order_Number) FROM [dbo].[Order] 
--go

---<--! Select Table Order !-->---
select [Order_Date] as "Дата заказа",[Order_Time] as "Время заказа",[Order_Number] as "Номер заказа",[Order_Status] as "Статус заказа" from [dbo].[Order]
inner join [dbo].[Category] on [Category_ID] = [ID_Category]
inner join [dbo].[Client] on [Client_ID] = [ID_Client]
inner join [dbo].[Driver] on [Driver_ID] = [ID_Driver]
order by [Order_Number] ASC
go

--select ID_Order as 'ID', [Order_Date] as "Дата заказа",[Order_Time] as "Время заказа",[Order_Number] as "Номер заказа",[Order_Status] as "Статус заказа" from [dbo].[Order] inner join [dbo].[Category] on [Category_ID] = [ID_Category] inner join [dbo].[Client] on [Client_ID] = [ID_Client] inner join [dbo].[Driver] on [Driver_ID] = [ID_Driver] where [Order_Status] not like 'Закрыт'
--go

select [ID_Order] as 'ID' ,[Order_Date] as 'Дата заказа',[Order_Time] as 'Время заказа',[Order_Number] as 'Номер заказа',[Order_Status] as 'Статус заказа' from [dbo].[Order] inner join [dbo].[Category] on [Category_ID] = [ID_Category] inner join [dbo].[Client] on [Client_ID] = [ID_Client] inner join [dbo].[Driver] on [Driver_ID] = [ID_Driver]
go

select [Order_Number] as 'Номер заказа',[Order_Date] as "Дата заказа",[Order_Time] as "Время заказа", [Order_Status] as "Статус заказа" from [dbo].[Order]
where [Client_ID] = '3'
order by [Order_Number]
go

---<--! Create Table Start_Point !-->---
IF (OBJECT_ID('dbo.Start_Point') IS NULL) CREATE TABLE [dbo].[Start_Point]
(
	[ID_Start_Point] [int] not null identity(1,1),
	[Start_Point_Name] [varchar] (max) not null,
	[Order_ID] [int] not null,
	constraint [PK_Start_Point] primary key clustered ([ID_Start_Point] ASC) on [PRIMARY],
	constraint [FK_Start_Point_Order] foreign key ([Order_ID]) references [dbo].[Order] ([ID_Order]) 
)
go

---<--! Insert Table Start_Point !-->---
insert into [dbo].[Start_Point] ([Start_Point_Name],[Order_ID])
values ('г. Москва, ул. Волжская, д.10, к.1',1),
('г. Москва, ул. Волжская, д.11, к.2',2),
('г. Москва, ул. Волжская, д.7, к.1',3),
('г. Москва, ул. Волжская, д.24, к.1',4),
('г. Москва, ул. Волжская, д.9, к.3',5)
go

---<--! Select Table Start_Point !-->---
select [Start_Point_Name] as 'Пункт Назначения', [Order_ID] as 'Заказ ID' from [dbo].[Start_Point]
inner join [dbo].[Order] on [Order_ID] = [ID_Order]
order by [Start_Point_Name] ASC
go

--update [dbo].[Start_Point] set [Start_Point_Name] = '', [Order_ID] = '1' where [ID_Start_Point] = 1
--go

---<--! Create Table End_Point !-->---
IF (OBJECT_ID('dbo.End_Point') IS NULL) CREATE TABLE [dbo].[End_Point]
(
	[ID_End_Point] [int] not null identity(1,1),
	[End_Point_Name] [varchar] (max) not null,
	[Order_ID] [int] not null,
	constraint [PK_End_Point] primary key clustered ([ID_End_Point] ASC) on [PRIMARY],
	constraint [FK_End_Point_Order] foreign key ([Order_ID]) references [dbo].[Order] ([ID_Order]) 
)
go

---<--! Insert Table End_Point !-->---
insert into [dbo].[End_Point] ([End_Point_Name],[Order_ID])
values ('г. Москва,  ул. Волоколамская, д.6, стр. 5',1),
('г. Москва,  ул. Волоколамская, д.12, стр. 1',2),
('г. Москва,  ул. Волоколамская, д.6, к2',3),
('г. Москва,  ул. Волоколамская, д.1, стр. 1',4),
('г. Москва,  ул. Волоколамская, д.12, к3',5)
go

---<--! Select Table End_Point !-->---
select [End_Point_Name] as "Пункт Назначения" from [dbo].[End_Point]
inner join [dbo].[Order] on [Order_ID] = [ID_Order]
order by [End_Point_Name] ASC
go


---<--! Create Table Receipt !-->---
IF (OBJECT_ID('dbo.Receipt') IS NULL) CREATE TABLE [dbo].[Receipt]
(
	[ID_Receipt] [int] not null identity(1,1),
	[Receipt_Numm] [int] not null,
	[Receipt_Date] [Date] not null default(getDate()),
	[Receipt_Time] [Time] not null default(getDate()),
	[Paid_Summ] [Decimal] (38,2) not null,
	[With_NDC] [Decimal] (38,2) not null,
	[Without_NDC] [Decimal] (38,2) not null,
	[Order_ID] [int] not null,
	constraint [CH_Paid_Summ] check ([Paid_Summ] >= 0),
	constraint [CH_With_NDC] check ([With_NDC] >= 0),
	constraint [CH_Without_NDC] check ([Without_NDC] >= 0),
	constraint [PK_Receipt] primary key clustered ([ID_Receipt] ASC) on [PRIMARY],
	constraint [FK_Receipt_Order] foreign key ([Order_ID]) references [dbo].[Order] ([ID_Order]) 
)
go

---<--! Insert Table Receipt !-->---
insert into [dbo].[Receipt] ([Receipt_Numm],[Receipt_Date],[Receipt_Time],[Paid_Summ],[With_NDC],[Without_NDC],[Order_ID])
values ('1','15.12.2021','13:01:42','900','804.00','670.0','1'),
('2','15.12.2021','13:04:49','900',870.00,720.0,2),
('3','15.12.2021','9:01:32','700',1200.00,1000.0,3),
('4','15.12.2021','11:06:12','1200',804.00,670.0,4),
('5','15.12.2021','12:01:12','1500',820.00,675.0,5)
go

---<--! Select Table Receipt !-->---
select [Receipt_Numm] as "Номер чека",[Receipt_Date] as "Дата чека",[Receipt_Time] as "Время чека",[Paid_Summ] as "Внесённая сумма",[With_NDC] as "С НДС",[Without_NDC] as "БЕЗ НДС" from [dbo].[Receipt]
inner join [dbo].[Order] on [Order_ID] = [ID_Order]
order by [Receipt_Numm] ASC
go


--select [Receipt_Numm] as 'Номер чека',[Receipt_Date] as 'Дата чека',[Receipt_Time] as 'Время чека',[Paid_Summ] as 'Внесённая сумма',[With_NDC] as 'С НДС',[Without_NDC] as 'БЕЗ НДС', [Order_ID] as 'Order_ID' from [dbo].[Receipt] inner join [dbo].[Order] on [Order_ID] = [ID_Order] where [Order_ID] = '1'
--go

---<--! Create Table FeedbackMark !-->---
IF (OBJECT_ID('dbo.FeedbackMark') IS NULL) CREATE TABLE [dbo].[FeedbackMark]
(
	[ID_FeedbackMark] [int] not null identity(1,1),
	[FeedbackMark_Name] [varchar] (32) not null,
	constraint [PK_FeedbackMark] primary key clustered ([ID_FeedbackMark] asc) on [PRIMARY],
	constraint [UQ_FeedbackMark_Name] unique ([FeedbackMark_Name])
)
go

---<--! Insert Table Mark !-->---
insert into [dbo].[FeedbackMark] ([FeedbackMark_Name])
values ('1'),('2'),('3'),('4'),('5')
go

---<--! Select Table Mark !-->---
select [FeedbackMark_Name] as "Оценка" from [dbo].[FeedbackMark]
go

---<--! Create Table Feedback !-->---
IF (OBJECT_ID('dbo.Feedback') IS NULL) CREATE TABLE [dbo].[Feedback]
(
	[ID_Feedback] [int] not null identity(1,1),
	[Feedback_Text] [varchar] (max) not null,
	[Feedback_Mark] [int] not null,
	[Order_ID] [int] not null,
	constraint [UQ_Order_ID_Feedback] unique ([Order_ID]),
	constraint [PK_Feedback] primary key clustered ([ID_Feedback] ASC) on [PRIMARY],
	constraint [FK_Feedback_Order] foreign key ([Order_ID]) references [dbo].[Order] ([ID_Order]),
	constraint [MinMaxFeedback_Mark] check ([Feedback_Mark] <= 5 AND [Feedback_Mark] >= 2),
)
go

---<--! Insert Table Feedback !-->---
insert into [dbo].[Feedback] ([Feedback_Text],[Feedback_Mark],[Order_ID])
values ('Ваще крутой водила 100/10',5,5),
('Не оч круто, молчал всю дорогу',2,3)
go

update [dbo].[Feedback] set [Feedback_Text] = '123', [Feedback_Mark] = 2 where [Order_ID] = '3'
go

select [ID_Feedback] as 'ID', [Feedback_Text] as "Текст отзыва", [Feedback_Mark] as "Оценка отзыва" from [dbo].[Feedback] 
where [Order_ID] = [Order_ID]
go

---<--! Select Table Feedback !-->---
select [Feedback_Text] as "Текст отзыва" from [dbo].[Feedback]
inner join [dbo].[Order] on [Order_ID] = [ID_Order]
order by [Order_ID] ASC
go
---<--! Create Table Mark !-->---
IF (OBJECT_ID('dbo.Mark') IS NULL) CREATE TABLE [dbo].[Mark]
(
	[ID_Mark] [int] not null identity(1,1),
	[Mark_Name] [varchar] (32) not null,
	constraint [PK_Mark] primary key clustered ([ID_Mark] asc) on [PRIMARY],
	constraint [UQ_Mark_Name] unique ([Mark_Name])
)
go

---<--! Insert Table Mark !-->---
insert into [dbo].[Mark] ([Mark_Name])
values ('Geely'),('Audi'),('Hyundai')
go

---<--! Select Table Mark !-->---
select [Mark_Name] as "Марка" from [dbo].[Mark]
order by [Mark_Name] ASC
go


---<--! Create Table Model !-->---
IF (OBJECT_ID('dbo.Model') IS NULL) CREATE TABLE [dbo].[Model]
(
	[ID_Model] [int] not null identity(1,1),
	[Model_Name] [varchar] (32),
	[Mark_ID] [int] not null,
	constraint [PK_Model] primary key clustered ([ID_Model] ASC) on [PRIMARY],
	constraint [UQ_Model_Name] unique ([Model_Name]),
	constraint [FK_Model_Mark] foreign key ([Mark_ID]) references [dbo].[Mark] ([ID_Mark]) 
)
go

---<--! Insert Table Model !-->---
insert into [dbo].[Model] ([Model_Name],[Mark_ID])
values ('Emgrand 7',1),('A4 Allroad Quattro',2),('Elantra',3)
go

---<--! Select Table Model !-->---
select [Model_Name] as "Модель" from [dbo].[Model]
inner join [dbo].[Mark] on [Mark_ID] = [ID_Mark]
order by [Mark_ID] ASC
go


---<--! Create Table Transport !-->---
IF (OBJECT_ID('dbo.Transport') IS NULL) CREATE TABLE [dbo].[Transport]
(
	[ID_Transport] [int] not null identity(1,1),
	[Production_Year] [int] not null,
	[Place_Count] [int] not null,
	[Reg_Number] [varchar] (16) not null,
	[Model_ID] [int] not null,
	[Color_ID] [int] not null,
	[Body_Type_ID] [int] not null,
	[Firm_ID] [int] not null,
	constraint [PK_Transport] primary key clustered ([ID_Transport] ASC) on [PRIMARY],
	constraint [UQ_Reg_Number] unique ([Reg_Number]),
	constraint [FK_Transport_Model] foreign key ([Model_ID]) references [dbo].[Model] ([ID_Model]),
	constraint [FK_Transport_Color] foreign key ([Color_ID]) references [dbo].[Color] ([ID_Color]),
	constraint [FK_Transport_Body_Type] foreign key ([Body_Type_ID]) references [dbo].[Body_Type] ([ID_Body_Type]),
	constraint [FK_Transport_Firm] foreign key ([Firm_ID]) references [dbo].[Firm] ([ID_Firm])
)
go

---<--! Insert Table Transport !-->---
insert into [dbo].[Transport] ([Production_Year],[Place_Count],[Reg_Number],[Model_ID],[Color_ID],[Body_Type_ID],[Firm_ID])
values ('2007', '5', 'HK001 77RUS',1,1,1,1)
go

---<--! Select Table Transport !-->---
select [Production_Year] as 'Год производства',[Place_Count] as 'Количество мест',[Reg_Number] as 'Регистрационный номер',[Body_Type_Name] as 'Тип корпуса', [Model_Name] as 'Модель', [Color_Name] as 'Цвет', [Firm_Name] as 'Фирма' from [dbo].[Transport]
inner join [dbo].[Model] on [Model_ID] = [ID_Model]
inner join [dbo].[Color] on [Color_ID] = [ID_Color]
inner join [dbo].[Body_Type] on [Body_Type_ID] = [ID_Body_Type]
inner join [dbo].[Firm] on [Firm_ID] = [ID_Firm]
go

---<--! Create Table Act_Destroy !-->---
IF (OBJECT_ID('dbo.Act_Destroy') IS NULL) CREATE TABLE [dbo].[Act_Destroy]
(
	[ID_Act_Destroy] [int] not null identity(1,1),
	[Destroy_Why] [varchar] (max) not null,
	[Destroy_Date] [Date] default(getDate()),
	[Transport_ID] [int] not null,
	[TManager_ID] [int] not null,
	constraint [PK_Act_Destroy] primary key clustered ([ID_Act_Destroy] ASC) on [PRIMARY],
	constraint [FK_Act_Destroy_Transport] foreign key ([Transport_ID]) references [dbo].[Transport] ([ID_Transport]),
	constraint [FK_Act_Destroy_TManager] foreign key ([TManager_ID]) references [dbo].[TManager] ([ID_TManager]),
)
go

---<--! Insert Table Act_Destroy !-->---
insert into [dbo].[Act_Destroy] ([Destroy_Why],[Destroy_Date],[Transport_ID],[TManager_ID])
values ('Физический износ','24.04.2022',1,1),
go

---<--! Select Table Act_Destroy !-->---
select[Destroy_Why] as "Причина списания",[Destroy_Date] as "Дата списания" from [dbo].[Act_Destroy]
inner join [dbo].[Transport] on [Transport_ID] = [ID_Transport]
inner join [dbo].[TManager] on [TManager_ID] = [ID_TManager]
order by [Destroy_Date] ASC
go

---<--! Create Table Combination_Transport_Feature !-->---
IF (OBJECT_ID('dbo.Combination_Transport_Feature') IS NULL) CREATE TABLE [dbo].[Combination_Transport_Feature]
(
	[ID_Combination_Transport_Feature] [int] not null identity(1,1),
	[Transport_ID] [int] not null,
	[Feature_ID] [int] not null,
	constraint [PK_Combination_Transport_Feature] primary key clustered ([ID_Combination_Transport_Feature] ASC) on [PRIMARY],
	constraint [FK_Combination_Transport] foreign key ([Transport_ID]) references [dbo].[Transport] ([ID_Transport]),
	constraint [FK_Combination_Feature] foreign key ([Feature_ID]) references [dbo].[Feature] ([ID_Feature])
)
go

---<--! Insert Table Combination_Transport_Feature !-->---
insert into [dbo].[Combination_Transport_Feature] ([Transport_ID],[Feature_ID])
values (1,1)
go

---<--! Select Table Combination_Transport_Feature !-->---
select [Transport_ID] as "ID Transport",[Feature_ID] as "ID Feature" from [dbo].[Combination_Transport_Feature]
inner join [dbo].[Transport] on [ID_Transport] = [Transport_ID]
inner join [dbo].[Feature] on [ID_Feature] = [Feature_ID]
go

---<--! Create Table Combination_Transport_Driver !-->---
IF (OBJECT_ID('dbo.Combination_Transport_Driver') IS NULL) CREATE TABLE [dbo].[Combination_Transport_Driver]
(
	[ID_Combination_Transport_Driver] [int] not null identity(1,1),
	[Transport_ID] [int] not null,
	[Driver_ID] [int] not null,
	constraint [PK_Combination_Transport_Driver] primary key clustered ([ID_Combination_Transport_Driver] ASC) on [PRIMARY],
	constraint [FK_Combination_Transport2] foreign key ([Transport_ID]) references [dbo].[Transport] ([ID_Transport]),
	constraint [FK_Combination_Driver] foreign key ([Driver_ID]) references [dbo].[Driver] ([ID_Driver])
)
go

---<--! Insert Combination_Transport_Driver !-->---
insert into [dbo].[Combination_Transport_Driver] ([Transport_ID],[Driver_ID])
values (1,1)
go

---<--! Select Combination_Transport_Driver !-->---
select [Transport_ID] as "ID Transport",[Driver_ID] as "ID Driver" from [dbo].[Combination_Transport_Driver]
inner join [dbo].[Transport] on [ID_Transport] = [Transport_ID]
inner join [dbo].[Driver] on [ID_Driver] = [Driver_ID]
go