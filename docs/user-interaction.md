# Functionalities

The website will offer the following functionalities:

- **User registration and Authentication and logout.** [FUNC-0]
  - It shall allow to register new users, as well as to allow to change the 
  basic registration information and password for all users. A user will be 
  authenticated by entering his email and password. The authenticated user will 
  be able to explicitly exit the web site.


- **Product search.** [FUNC-1]
  - A user, even if not authenticated, will be able to search for products 
  by means of a form. The form will be displayed on the main screen of the 
  application. The search form will display four fields: a box where the user will enter 
  product names, a drop-down for categories, a drop-down for tags and a price range. 
  The application will display (below the search form) all products that meet the 
  requirements. None of the fields are mandatory. The more fields are filled in, 
  the more selective the search will be. For each product that appears as a search result, 
  its name, category and average rating will be displayed. The products will be displayed 
  paginated and sorted by price (in descending order from highest to lowest). A user 
  (authenticated or not) will be able to click on the name of a product to visualize (in 
  another screen) the detailed information of the product, namely its name, category, 
  description, image, price, average rating, the number of comments it has and in case of 
  having these same paginated in the same way.


- **Viewing the cart.** [FUNC-2]
  - A user, authenticated or not, can add one or more products to his 
  cart and modify the quantity. You will also be able to see your current cart with all 
  the products added so far. For this, you will see a list with all the products (for each 
  product you will have its name, price, quantity and a link to see more detailed 
  information of the product) and the total price of the cart.


- **Placing an order.** [FUNC-3]
  - If the user is authenticated, the shopping cart will include a shopping 
  area which will display a form to place the order. The form will include a field to enter 
  on whose behalf the order is being placed, a field to enter the bank card with which the 
  user wishes to pay for the order and an address where the order will be sent. If 
  everything is entered correctly and the order is placed without any problem, another page 
  will open showing a summary of the order with the following information: an identifier, 
  the name of the user who placed the order, a list of all the products, the total price of 
  the order, the address to which the order will be sent, and the estimated waiting time. 
  It will also present the option to return to the main page to continue shopping. 
  Otherwise, it will display an error message indicating why the registration cannot be 
  completed.


- **View order history.** [FUNC-4]
  - An authenticated user can view the orders he/she has placed over 
  time. The orders will be displayed paginated, showing the most recent ones first. For 
  each order, the date and time it was placed, the name of the person who placed the order, 
  the shipping status, the total price and a link to view more detailed information about 
  the order will be displayed.


- **View order.** [FUNC-5]
  - Once the user places an order and goes to the order history he/she can 
  choose to view more detailed information about the order. Here you can see, in addition 
  to all the products in the same way as in the cart, a summary of the information used 
  to make it, that is, the name of the account holder, the name of the person who made it, 
  the last 4 of the card used, the address to which it was sent, the status of the order 
  and if it has not arrived, the estimated date of arrival. In addition, a link/button 
  will also be included to rate the product if you have not already done so.


- **Product rating.** [FUNC-6]
  - Only authenticated users will be able to rate a product. To do so, a 
  link/button will be included in the detailed information of each product that when 
  clicked will display a screen that includes: a link to return to the previous screen, 
  the name of the product and a form with a drop-down field that allows the user to enter 
  the rating (from 1 to 5). The web application will verify that the user did not already 
  rate the product. In case everything works fine, a success message will be displayed and 
  the user will return to the product display screen. Otherwise, an error message will be 
  displayed showing the reason why the rating could not be entered.


- **Making comments.** [FUNC-7]
  - An authenticated user will be able to comment on the products, but 
  all users will be able to see them. To do this, on the screen where the product details 
  are shown, if authenticated, there will be an area with a field to enter a text and a 
  button to make a comment. Once confirmed, the comment will show who made it, the date 
  and time it was made and the message entered. 


The web application must also deal with the internationalization of messages, dates and monetary amounts.