# Design and Implementation of Scholarly Article Ranking System 

## Log
**Version v.0.1.2(5/2/2018 11:20:36 AM )**

* we reorganize the author page, paper_info page and add some functions .
* we rewrite some cqls which have a better performance. 
	

**Version v.0.1.1(4/26/2018 9:48:56 PM )**

* rewrite search page 
	1. we use template engine`Thymeleaf` instead of `Ajax` to implement this page.
	2. we add some function including different rank type, show some important authors, affiliations, journals and conferences
	3. we implement search author in  Search page.
* we redesign the neo4j database, specially for `Paper` node.
* fix some bug in this page. 


**Version v.0.1.0(4/6/2018 3:21:04 PM )**

* config Database, design schema, import data in Neo4j.
* write a fulltext index plugin with `IKanalyzer-5.5` for Neo4j which supports English and Chinese analyzer  
* Use `SDN` to build a demonstration 
* finish search page, author page, paper page with little visualization.  


## Database
Scholarly Article Data:  MAG(Microsoft Academic Graph ) records articles of various disciplines from 1800 to 2016 . It contains around 127 million articles, 115 million authors, 24,024 venues and 529 million citations.

We use a popular graph database [Neo4j](http://neo4j.com) for scholarly articles, due to the heterogeneous, evolving and dynamic nature of entities involved in scholarly articles. According the data structure of scholarly articles, we design the database schema in `Fig 1`.


## Persistence Access [SDN](https://projects.spring.io/spring-data-neo4j/)
We use `Spring-Data-Neo4j 5.0.5` to build the project.
  
TODO: draw flow chart of the system. 


## Pages

**Search Page**

support search with keywords, author. rank with `SARANK Algorithm`,`Relevance Rank`, `Time Rank`, `Citation Rank` and pagaination. 
![](https://i.imgur.com/8QOhOZA.png) 


**Paper Details Page**
detailed information for a specific paper
![](https://i.imgur.com/edNXzvf.png)

**Author Details Page**
detailed author information for a specific author
![](https://i.imgur.com/3GHVOdo.png)

## The Stack
These are components of our Web Application: 

*	Application Type: Spring-Boot Java Web Application (Jetty) 
*	Persistence Access： `SDN`（Spring-Data-Neo4j 5.0.5）
*	Database： neo4j-community-3.3.1
*	Frontend: jquery2.1.4, bootstrap v3.3.7,  EChart 4.0.4 
