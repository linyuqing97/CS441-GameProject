# CS441-GameProject
This is the first part of the final game project for CS441, a basic framwork structure of the game.
Objective:
    Throughout the experience of developing Android applications over the summer, I found out that android developing is not friendly. So in this project, I stand on the Apple side and I'm going to make a game that player can earn points by operating their little Apple icon to "eat" Android AHahahah. 
 
First, I created a splash screen with an "Apple eat Android" image which could also be the icon of the game. I scale the X and Y-axis of the image, so it fills out the whole screen. The splash screen would only last for 3 seconds then it will automatically enter the first main activity which is an instruction page. 
In order to easily edit the instruction,  I form the instruction page with htmlText view which can generate an HTML page by an HTML like String. Since the game is still in process, and I still thinking about the rules, I just write down some basic stuff with no background or images. A button will take players to another activity. I have made the HTML textView in Scroll View so that the Button would always hand on the button of the activity. 

The third activity is called name activity which asking user's name. From here user can access the leaderboard from the leaderboard button or the user can enter the game with the enter button. Since the game still in process and I haven't set up a terminating point of the game, play's name will not display in the leaderboard unless they enter the leaderboard with their name. In the second half of the project, I will carry the user's name through the game activity and display it on the leaderboard form there.

In the leaderboard class, I have created a class as an object to hold the player's name, name of the game, and score. All information would be displayed in the leaderboard as recycle view. The leaderboard would only display up to 20 players based on top score. Player objects are stored in a java ArrayList and I have also implemented a sorting method for the Arraylist on descending order base on the user's score.  The leaderboard also has saved and load method to keep previous players' information. In addtion, in the leader board activity, the upload button would fire a url with the player 's information(depends on who has the highest score) and score to the store on server through PHP and MySQL databass. The get button would get the most 10 recently players information from the web as Json, extract the Json and display it in the leader board.

For the game view, I implement Bitmap and drawing which I will explain more details in the second half of the project. 


The second part(Gameplay):
The gameplay is very simple. I used an API called Bitmap, which allows me to draw pictures on the screen by giving the size of the picture and its X and Y-axis. Therefore, I can change icons' X and Y axis to make it move on the screen. The apple icon has a fix X-axis with a changing Y-axis control by the touch event handler. Each tap on the screen would decrease the value of Y-axis which would raise the Apple on the screen. "Android" and other objectives would have a random Y-axis and a changing x value, so objects would move from right to left horizontally. Objects such as life, score, and level are drawn on the screen with a fix X and Y coordination. I created a game view class to handle all the drawing part on the screen. In addition, I have set an internal timer which would increase the objects move speed over time which increases the level of difficulties. The whole gameplay is held by a class called Game which is another timer and Task schedule that run the game view class over and over with no delay. In order to terminate the game after life counts become 0, a synchronized is also running to interrupt the game and start a new intent that leads to the Result activity.
The Result activity has two buttons,"Try Again" would initial the game again by firing another intent to Game class; "Save" would create a player information object to store player's scores and name then leads to the leaderboard activity.
In addition, I also add some background images and a round button XML file for version control. Making the game looks much nicer.  
 


Time line:
7/19:Initial the proejct
7/20:Added splash screen
7/21:Added HTML View and scroll view for instructions
7/22:Added leader board with recycleview and save load method.
7/23:Added gameview by implementing Bitmap and draw method,touchhanderler, some basic gameplay.
7/25:Added sorting method, changed leaderboard layout,ready to take in data from online.
7/26:Fixed some bugs on the sorting method,edit README,added launch icon.
7/30: Added communication to PHP on the sever
8/1: Improved the game play, hit android icon can now increase score, hit other object now decrease life count
8/3: added termination result screen
8/5: Connect reuslt activity to restart game and leadboard, now has some basic game cycle.
8/6: Fix leaderboard bugs for displaying scores
8/8: Leaser board now can post and retrive scores
8/9: Added background and round buttons to the game, now the game looks nice



