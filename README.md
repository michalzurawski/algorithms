# algorithms

This project, based on [Algorithms course](http://algs4.cs.princeton.edu/home/) from Princeton University,
contains few algorithms and their usage implementation.

All projects require Java 8 and Maven.

Please not that main goal of this projects is efficiency (both time and memory) hence using *Arrays*
instead of *ArrayList* or *byte[]* instead of *Enum* values.

If not stated otherwise in a specific project you may compile each module using:
`mvn compile assembly:single` from specific project directory.
To run each project please use `java -jar target/$PROJECT_JAR`.

## Algorithms

Currently this project contains following algorithms:

1. [Percolation](#percolation)
2. [Queues](#queues)

### Percolation

Given a composite systems comprised of randomly distributed insulating and metallic materials:
what fraction of the materials need to be metallic so that the composite system is an electrical conductor?
Given a porous landscape with water on the surface (or oil below),
under what conditions will the water be able to drain through to the bottom
(or the oil to gush through to the surface)?
Scientists have defined an abstract process known as [percolation](https://en.wikipedia.org/wiki/Percolation)
to model such situations.

**The model.** We model a percolation system using an *n*-by-*n* grid of sites.
Each site is either open or blocked.
A full site is an open site that can be connected to an open site in the top row
via a chain of neighboring (left, right, up, down) open sites.
We say the system *percolates* if there is a full site in the bottom row.
In other words, a system percolates if we fill all open sites connected to the top row
and that process fills some open site on the bottom row.
(For the insulating/metallic materials example, the open sites correspond to metallic materials,
so that a system that percolates has a metallic path from top to bottom,
with full sites conducting. For the porous substance example,
the open sites correspond to empty space through which water might flow,
so that a system that percolates lets water fill open sites, flowing from top to bottom.)

![Percolates](percolation/percolates-yes.png "Percolates")
![Percolates-no](percolation/percolates-no.png "Does not percolate")

**The problem.** In a famous scientific problem, researchers are interested in the following question:
if sites are independently set to be open with probability *p* (and therefore blocked with probability *1 − p*),
what is the probability that the system percolates?
When *p* equals *0*, the system does not percolate; when *p* equals *1*, the system percolates.
The plots below show the site vacancy probability *p* versus the percolation probability for
*20*-by-*20* random grid (upper) and *100*-by-*100* random grid (lower).

![Percolation-20](percolation/percolation-threshold20.png "Percolation threshold for 20-by-20 grid")
![Percolation-20](percolation/percolation-threshold100.png "Percolation threshold for 20-by-20 grid")

When *n* is sufficiently large, there is a threshold value _p*_ such that when _p_ < _p*_
a random *n*-by-*n* grid almost never percolates, and when _p_ > _p*_,
a random *n*-by-*n* grid almost always percolates.
No mathematical solution for determining the percolation threshold _p*_ has yet been derived.
This program estimates the threshold value _p*_ using [Monte Carlo simulation](https://en.wikipedia.org/wiki/Monte_Carlo_method).

**Runing the program**

Build the project using
```
$ mvn compile assembly:single
```
then run it like `java -jar $JAR gridSize trialsNumber`.
This is going to compute *trialsNumber* simulations on *gridSize*-by-*gridSize* grid.

Example usage:

```
$ java -jar target/percolation-1.0-SNAPSHOT-jar-with-dependencies.jar 100 2000
mean                    = 0.5930502000000007
stddev                  = 0.015540738954233215
95% confidence interval = [0.5923690970850447, 0.5937313029149568]
```

### Queues

**Dequeue.** A double-ended queue or deque (pronounced "deck") is a generalization of a stack and a queue
that supports adding and removing items from either the front or the back of the data structure.

**Randomized queue.** A randomized queue is similar to a stack or queue, except that the item removed
is chosen uniformly at random from items in the data structure.

**Permutation client.** Client program that takes a command-line integer *k*,
reads in a sequence of strings from standard input and prints exactly *k* of them, uniformly at random.
Assumption is made that *0* ≤ *k* ≤ *n*, where *n* is the number of string on standard input.

Example usage:
```
$ cat examples/distinct.txt 
A B C D E F G H I
$ java -jar target/queues-1.0-SNAPSHOT-jar-with-dependencies.jar 3 < examples/distinct.txt 
H
G
F
```

