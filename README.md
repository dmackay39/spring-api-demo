# spring-api-demo
A demonstration of a basic API using Spring. There are examples of different inheritance strategies, different database relationships, and custom queries. 
I plan on adding to this with some more full explanations of what is going on.

## Notes
### Structure
* Model - Classes annotated with @Entity represent the data which will be used to reflect/construct the underlying SQL database.
* Repository - The go-between for the application and the database.
* Service - Contains the logic for the API calls.
* Controller - Accepts the HTTP requests from the front end and passes them to the service layer to be processed.

### Model
* Key articles: https://www.baeldung.com/jpa-entities, https://projectlombok.org/features/Data
* Classes which you want to be present in the SQL database are annotated with @Entity. This also requires the class to have an Id (which can be inherited from a super class) and a no-arguments constructor.
* Annotating them with @Data generates getters and setters for all fields, a required args constructor (a constructor with the fields required to be non-null as parameters), and toString, equals, and hashCode methods. If the class extends another class, the @EqualsAndHashCode(callSuper=true) annotation will create equals and hashCode methods which take into account the inherited fields as well as the in-class fields.
* When doing OneToMany and ManyToOne annotations, think about it in these terms: One (class name) To Many (field name). Eg One author has a list of books, so we put the annotation @One(author)ToMany(books) on the List<Books> in the author class. And similarly, in the book class, there are Many (books) to One (author) so we put @ManyToOne on the author field.
 * In these relationships, we put a JsonManagedReference annotation on the List and a JsonBackReference reference on the single field. This controls the recursion when returning the JSON objects so that there won't be an error due to infinite looping. Eg if each book has an author, and each author has a list of books containing that book, and that book has that author etc...
* Enum fields are annotated with @Enumerated with an argument depending on whether the enum is ordinal or string. Eg an ordered set of enums like the days in the week might be ordinal, but something like names of colours would be EnumType.String.   
* See my other github repository for info on the inheritance strategies employed here: https://github.com/dmackay39/spring-inheritance-strategies

#### Repository
* Key articles: https://www.baeldung.com/spring-data-jpa-query
* Annotated with @Repository and extends CrudRepository for basic CRUD functions (eg save, deleteById etc).
* It is possible to make custom queries through two methods.
  * Syntax: In IAuthorRepository, the programme figures out what is looked for by the syntax of the method name. findByNameContains(String filter) returns authors whose name contains the string simply from the name of the method.
  * Custom query: Using the @Query annotation, a custom query can be made with a little knowledge of SQL. By default, this is actually the JPQL query language (https://openjpa.apache.org/builds/1.2.3/apache-openjpa/docs/jpa_langref.html). This is similar to SQL, but rather than using the columns of the database, it uses the fields of the object and then converts it in the background. Eg in the query, it uses a.movies - the movies field of Author - and then in the background conducts queries of the database. The JPQL query I wrote is:
    * SELECT a FROM Author a WHERE SIZE(a.movies)>0 ORDER BY SIZE(a.movies)
  * and the SQL queries it conducts behind the scenes to fulfil it are:
    * select a1_0.id,a1_0.name from author a1_0 where (select count(1) from lendable_material m1_0 where a1_0.id=m1_0.screenwriter_id)>0 order by (select count(1) from lendable_material m2_0 where a1_0.id=m2_0.screenwriter_id)
    * select m1_0.screenwriter_id,m1_0.id,m1_0.is_available,m1_0.title,d1_0.id,d1_0.name,m1_0.movie_genre from (select * from lendable_material t where t.material_type='Movie') m1_0 left join director d1_0 on d1_0.id=m1_0.director_id where m1_0.screenwriter_id=?
