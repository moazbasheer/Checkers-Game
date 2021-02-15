# Checkers Game
* Java GUI project using javax.swing 

## Game rules
* Pieces typically move one step diagonally forward only.
* If a piece reaches the last row of the opponent it becomes a king.
* A king can move backwards as well as forwards.
* Attacking an opponent is possible when a piece of the opponent is adjacent to the player's piece and the square diagonally after the opponent piece is free. In which case the player can jump to the free square and the opponent's piece is removed from the board.
* If it is your turn after attacking and you can still attack and you can continue attacking until there are no more attacks possible or the game is over.
* If there are pieces that can attack, your next move must be an attack. You will not be able to move any piece that does not attack.
* Any piece even if it is a king cannot attack double pieces places diagonally directly after each other.
* A player loses if he/she does not have any pieces left or itâs his turn and he cannot move.
