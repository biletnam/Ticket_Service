# Ticket_Service
Hold and reserve seats in movie theater

## Getting Started

Install Oracle Java 7.0 or 8.0
This is written in Java. Preferably open in Eclipse.

## About This Project

### Assumptions

Users hold tickets one at a time. Once tickets are held, line would prompt in console to ask user if a reservation should be placed. Answer would either be yes or no, capitalization is ignored. 

```
Would you like to commit to this reservation? (Yes or No)
Yes
```

Number of rows is limited to 26 due to limitation of alphabets. Each row is represented by an alphabet in order. 

Movie Theater is a rectangle, meaning all rows have the same number of seats.

```
Seating Chart:
A X X O O O O O O O O O O O O O O O O O O 
B O O O O O O O O O O O O O O O O O O O O 
C O O O O O O O O O O O O O O O O O O O O 
D O O O O O O O O O O O O O O O O O O O O 
E O O O O O O O O O O O O O O O O O O O O 
F O O O O O O O O O O O O O O O O O O O O 
G O O O O O O O O O O O O O O O O O O O O 
H O O O O O O O O O O O O O O O O O O O O 
I O O O O O O O O O O O O O O O O O O O O 
J O O O O O O O O O O O O O O O O O O O O 
K O O O O O O O O O O O O O O O O O O O O 
L O O O O O O O O O O O O O O O O O O O O 
M O O O O O O O O O O O O O O O O O O O O 
N O O O O O O O O O O O O O O O O O O O O 
O O O O O O O O O O O O O O O O O O O O O 
P O O O O O O O O O O O O O O O O O O O O 
Q O O O O O O O O O O O O O O O O O O O O 
R O O O O O O O O O O O O O O O O O O O O 
S O O O O O O O O O O O O O O O O O O O O 
T O O O O O O O O O O O O O O O O O O O O 
U O O O O O O O O O O O O O O O O O O O O 
V O O O O O O O O O O O O O O O O O O O O 
W O O O O O O O O O O O O O O O O O O O O 
X O O O O O O O O O O O O O O O O O O O O 
Y O O O O O O O O O O O O O O O O O O O O 
Z O O O O O O O O O O O O O O O O O O O O 
```

If user requested seats more than the number of available seats in the theater, request will be rejected. No seats are held. 

```
#R004
Your reservation cannot be placed due to lack of seats.
Available Seats: 1
Seating Chart:
A X X X O 
B X X X X 
C X X X X 
```

All seats held but not reserved would expire in 30 seconds, marking those seats available again. 

```
Your seat hold ID is #1
Your seats: 
A1 A2 
Seating Chart:
A X X O O 
B O O O O 
C O O O O 
Would you like to commit to this reservation? (Yes or No)
Your seat hold #1 has been timed out.
```

### Best Seats Definition

* If there are enough consecutive seats in a row for the requested number of seats, those consecutive seats will be given.

* If there are enough seats in the theater for the requested number of seats, but not all in the same row, seats will be separated. The ticket service will sort rows by their number of consecutive seats, and assign the highest number of consecutive seats to user, then assign the next highest number of consecutive seats and so on. 

* If there are not enough seats in the movie theater, no seats will be held.

## Author

Zuri Wong

## License

This project is not licensed.
