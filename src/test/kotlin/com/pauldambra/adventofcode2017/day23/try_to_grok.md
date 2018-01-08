```
set b 67
set c b
jnz a 2
jnz 1 5
mul b 100
sub b -100000
set c b
sub c -17000
set f 1
set d 2
set e 2
set g d
mul g e
sub g b
jnz g 2
set f 0
sub e -1
set g e
sub g b
jnz g -8
sub d -1
set g d
sub g b
jnz g -13
jnz f 2
sub h -1
set g b
sub g c
jnz g 2
jnz 1 3
sub b -17
jnz 1 -23
```

```
0: set b 67          : { b: 67 }
1: set c b           : { b: 67, c: 67 }
2: jnz a 2
3: jnz 1 5
4: mul b 100         : { b: 670, c: 67 }
5: sub b -100000     : { b: 100670, c: 67 }
6: set c b           : { b: 100670, c: 100670 }
7: sub c -17000      : { b: 100670, c: 83670 }
8: set f 1           : { b: 100670, c: 83670, f: 1 }
9: set d 2           : { b: 100670, c: 83670, d: 2, f: 1 }
    10: set e 2      : { b: 100670, c: 83670, d: 2, e: 2, f: 1 }
        11: set g d  : { b: 100670, c: 83670, d: 2, e: 2, f: 1, g: 2 }
        12: mul g e  : { b: 100670, c: 83670, d: 2, e: 2, f: 1, g: 4 } //g = g * e
        13: sub g b  : { b: 100670, c: 83670, d: 2, e: 2, f: 1, g: -100666 } // g = g - b
        14: jnz g 2 // if (g != 0) jump to 16
        15: set f 0
        16: sub e -1 : { b: 100670, c: 83670, d: 2, e: 1, f: 1, g: -100666 }
        17: set g e  : { b: 100670, c: 83670, d: 2, e: 1, f: 1, g: 1 }
        18: sub g b  : { b: 100670, c: 83670, d: 2, e: 1, f: 1, g: -100669 }
        19: jnz g -8
    20: sub d -1
    21: set g d
    22: sub g b
    23: jnz g -13
24: jnz f 2
25: sub h -1
26: set g b
27: sub g c
28: jnz g 2
29: jnz 1 3
30: sub b -17
31: jnz 1 -23
```


```
0: set b 67          : { b: 67 }
1: set c b           : { b: 67, c: 67 }
2: jnz a 2
3: jnz 1 5
4: mul b 100         : { b: 670, c: 67 }
5: sub b -100000     : { b: 100670, c: 67 }
6: set c b           : { b: 100670, c: 100670 }
7: sub c -17000      : { b: 100670, c: 83670 }
8: set f 1           : { b: 100670, c: 83670, f: 1 }
9: set d 2           : { b: 100670, c: 83670, d: 2, f: 1 }
    10: set e 2      : { b: 100670, c: 83670, d: 2, e: 2, f: 1 }
        do {
         //11: set g d
         g = d
         //12: mul g e
         g = g * e
         //13: sub g b  : { b: 100670, c: 83670, d: 2, e: 2, f: 1, g: -100666 } // g = g - b
         g = g - b
         //14: jnz g 2 // if (g != 0) jump to 16
         if (g == 0) {
 //        15: set f 0
           f = 0
         }
         //16: sub e -1 : { b: 100670, c: 83670, d: 2, e: 1, f: 1, g: -100666 }
         e -= 1
         //17: set g e  : { b: 100670, c: 83670, d: 2, e: 1, f: 1, g: 1 }
         g = e
         18: sub g b  : { b: 100670, c: 83670, d: 2, e: 1, f: 1, g: -100669 }
         //g -= b
        //19: jnz g -8
        } while (g != 0)
    20: sub d -1
    21: set g d
    22: sub g b
    23: jnz g -13
24: jnz f 2
25: sub h -1
26: set g b
27: sub g c
28: jnz g 2
29: jnz 1 3
30: sub b -17
31: jnz 1 -23
```

```
0: set b 67          : { b: 67 }
1: set c b           : { b: 67, c: 67 }
2: jnz a 2
3: jnz 1 5
4: mul b 100         : { b: 670, c: 67 }
5: sub b -100000     : { b: 100670, c: 67 }
6: set c b           : { b: 100670, c: 100670 }
7: sub c -17000      : { b: 100670, c: 83670 }
8: set f 1           : { b: 100670, c: 83670, f: 1 }
9: set d 2           : { b: 100670, c: 83670, d: 2, f: 1 }
    do {
      e = 2
        do {
         g = d  // always 2?
         g = g * e 
         g = g - b
         if (g == 0) {
           f = 0
         }
         e -= 1
         g = e
         g -= b // in order to get g to 0 e == b == 100670
        } while (g != 0)
      d -= 1
      g = d
      g -= b
    } while (g != 0)
24: jnz f 2
25: sub h -1
26: set g b
27: sub g c
28: jnz g 2
29: jnz 1 3
30: sub b -17
31: jnz 1 -23
```

```
0: set b 67          : { b: 67 }
1: set c b           : { b: 67, c: 67 }
2: jnz a 2
3: jnz 1 5
4: mul b 100         : { b: 670, c: 67 }
5: sub b -100000     : { b: 100670, c: 67 }
6: set c b           : { b: 100670, c: 100670 }
7: sub c -17000      : { b: 100670, c: 83670 }
8: set f 1           : { b: 100670, c: 83670, f: 1 }
9: set d 2           : { b: 100670, c: 83670, d: 2, f: 1 }
    do {
      e = 2
        do {
         f = 0
         e = b
         g = 0
        } while (g != 0)
      d -= 1
      g = d
      g -= b // g is only 0 when d == 100670
    } while (g != 0)
24: jnz f 2
25: sub h -1
26: set g b
27: sub g c
28: jnz g 2
29: jnz 1 3
30: sub b -17
31: jnz 1 -23
```