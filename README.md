# Simple card checker

## Task definition.
Task is to design and implement a Kotlin/Java framework which provides methods to perform this filtering. Assume that a credit card number is valid if:
* it contains only numbers and no leading 0
* it is 12-19 digits long
* it passes the Luhn check https://en.wikipedia.org/wiki/Luhn_algorithm. For credit  
card numbers, the Luhn check digit is the last digit of the sequence.
In addition, connect to https://binlist.net API and put additional information about credit card in response to user of your framework.

| Card Number       | Brand            | Validity  |
| ----------------- | ---------------- | --------- |
| 4929804463622139  | Visa             | Valid     |
| 4929804463622138  | Visa             | Invalid   |
| 6762765696545485  | Maestro          | Valid     |
| 5212132012291762  | MasterCard       | Invalid   |
| 6210948000000029  | China Union Pay  | Valid     |


## Main features:
* Kotlin
* Library + sample project
* Unit tests
* Documentation
* Without any 3-party libraries
