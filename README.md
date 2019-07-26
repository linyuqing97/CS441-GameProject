# CS441-GameProject
This is the first part of the final game project for CS441, a basic framwork structure of the game.
Objective:
    Throughout the experience of developing Android applications over the summer, I found out that android developing is not friendly. So in this project, I stand on the Apple side and I'm going to make a game that player can earn points by operating their little Apple icon to "eat" Android AHahahah. 
 
First, I created a splash screen with an "Apple eat Android" image which could also be the icon of the game. I scale the X and Y-axis of the image, so it fills out the whole screen. The splash screen would only last for 3 seconds then it will automatically enter the first main activity which is an instruction page. 
In order to easily edit the instruction,  I form the instruction page with htmlText view which can generate an HTML page by an HTML like String. Since the game is still in process, and I still thinking about the rules, I just write down some basic stuff with no background or images. A button will take players to another activity. I have made the HTML textView in Scroll View so that the Button would always hand on the button of the activity. 

The third activity is called name activity which asking user's name. From here user can access the leaderboard from the leaderboard button or the user can enter the game with the enter button. Since the game still in process and I haven't set up a terminating point of the game, play's name will not display in the leaderboard unless they enter the leaderboard with their name. In the second half of the project, I will carry the user's name through the game activity and display it on the leaderboard form there.

In the leaderboard class, I have created a class as an object to hold the player's name, name of the game, and score. All information would be displayed in the leaderboard as recycle view. The leaderboard would only display up to 20 players based on top score. Player objects are stored in a java ArrayList and I have also implemented a sorting method for the Arraylist on descending order base on the user's score.  The leaderboard also has saved and load method to keep previous players' information. For testing purpose, I generate 20 players using a for loop with i%5 score.

For the game view, I implement Bitmap and drawing which I will explain more details in the second half of the project. 

Time line:
7/19:Initial the proejct
7/20:Added splash screen
7/21:Added HTML View and scroll view for instructions
7/22:Added leader board with recycleview and save load method.
7/23:Added gameview by implementing Bitmap and draw method,touchhanderler, some basic gameplay.
7/25:Added sorting method, changed leaderboard layout,ready to take in data from online.
7/26:Fixed some bugs on the sorting method,edit README.
