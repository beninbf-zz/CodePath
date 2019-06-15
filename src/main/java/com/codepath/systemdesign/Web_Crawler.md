1) Creating a web crawler
    - Given some set of URL's (desintations)
    - Will visit these urls, and essentially crawl those pages for information
      the destination uri is the root, and the crawler will travel to all child 
      pages
    - Might be able to provide structured input to the crawler.
   
2) Once the web crawler is seeded, creating an autonomous engine, that will build
    a product catalogue
    
 3) The goal of the product catalogue, compare prices for products across different domains. The catalogue that we're building, should, link back to the original data source (the link that was crawled that provided said information)
     
 
the crawler can determine a product page
     
logical schema: web crawler micro-service
     
// example crawler input: <amazon.com, amazon.com/electronic, amazon.com/electionrics/computers/, , , , , >
     
crawler_input: id, crawler_id, input_id
    
input: id, guid, uri

crawler: id, created_d, last_run_d
    
product_catalouge: id, crawler_id, type_cd, product_cd, product_id, price, uri, modified_d, created_i
define_idx(product_id)
    