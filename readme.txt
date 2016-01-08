Namho An
namho8211@gmail.com
COSI21a
PA02
Read me file for the hashtable experiments.

Different hash function has been tested. For each experiment, the number
of entries in the table is 13 and the number of random keys inserted into the table 
is 1000. Since the strings are randomly generated, the hash function
does not have significant impact on the distribution.
For example,

(1) the first hash function uses only the first letter in the string.
Here is the distribution:

distribution:
94 58 89 82 91 68 68 70 79 78 78 70 75 

(2) the second hash function uses the java string hashCode function.
Here is the distribution:


distribution:
85 71 81 83 75 77 74 74 77 78 77 69 79 

If the strings are not randomly generated, then the distribution could
be be even among all entries. For example, if we generate the strings
that all ends with either letter 'a' or letter 'b', 
the third hash function, which uses only the last letter, here is the
distribution, which is very bad.

distribution:
0 0 0 0 0 0 453 547 0 0 0 0 0 

