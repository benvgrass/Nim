# Ben Grass
# Nim using the theory of Definite Moves
import time



	

def move(gamestate, action): # takes a gamestate, and an action, and returns
	gamestate = list(gamestate)
	gamestate[action[0]] -= action[1]
	gamestate = [x for x in gamestate if x > 0]
	return tuple(gamestate)

def printboard(game): # prints the board
	counter  = 1
	for x in game:
		print "Row " + str((counter)) + ": " + "* " * x
		counter +=1
	print ""
	
def showwinner(player): # shows who has won
	print player + " has won!"
		

def possibleactions(game): # returns a list of the possible actions
	return reversed([(x, y+1) for x in range(len(game)) for y in range(game[x])])
		
def win(game): # returns if a player has won
	if len(game) == 0:
		return True
	return False
	
	
valuecache = {}
def value(game, player):
	# game = current game state
	# player = 1 if computer's turn, 0 if human player's
	# value = is this a good game state for the computer
	if (game, player) not in valuecache:
		if win(game):
			valuecache[(game,player)] = not player #if the game is over, return False if it's the computer's turn
		else:
			actions = possibleactions(game)
			newgames = map(lambda a: move(game, a), actions) #list of next game states
			values = map(lambda g: value(g, not player), newgames) #values of next game states
			if player:
				valuecache[(game,player)] = any(values)
			else:
				valuecache[(game,player)] = all(values)
	return valuecache[(game,player)]

def bestmove(game):
	print "Computer is making a move... \n"
	time.sleep(1.5)
	return max(possibleactions(game), key = lambda a: value(move(game, a), False))

	


		
def validmove(game, action):
	return action[1] <= game[action[0]]



# main:

def play():
	game = (1,3,5,7)
	printboard(game)
	while(True):
		boolean = True
		while(boolean):
			print "Your Move: "
			x = input("Row? ") - 1	
			y = input("Amount? ")
			action = (x,y)
			if validmove(game, action):
				game = move(game, action)
				boolean = False
			else:
				print "Invalid Move!\n"
				
		printboard(game)
		if win(game):
			showwinner("Player")
			break
		actions = possibleactions(game)
		compact = bestmove(game)
		if validmove(game, compact):
				game = move(game, compact)
		printboard(game)
		if win(game):
			showwinner("Computer")
			break

play()
