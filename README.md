Priority-Queue-and-Hash-Table-Algorithms
This project centered around implementing and testing algorithms for data structures, including priority queues, heaps, and hash tables using quadratic probing. It covered merging sorted data, implementing an earliest deadline first scheduler, building a hash table with quadratic probing, and conducting experiments with different symbol table implementations.

Features

Heap Operations: Completed merging functionality for two priority queues and verified the sortedness of the resulting merged list.
Earliest Deadline First Scheduler: Developed a scheduler that prioritized tasks based on their deadlines using a min-priority queue. Implemented mechanisms for scheduling tasks, processing tasks up to a specified time, and handling incomplete tasks.
Quadratic Probing Hash Table: Built a hash table that handled collisions using quadratic probing and dynamically resized based on the load factor.
Symbol Table Experiments: Compared various symbol table implementations, analyzing their performance using different text datasets for operations like get, put, and contains.

Skills Used
Data Structure Implementation: Developed algorithms for heaps, priority queues, and hash tables using quadratic probing.
Algorithm Design and Analysis: Designed efficient data structure operations and analyzed their performance across different inputs.
Scheduling Algorithms: Implemented real-time scheduling using a priority-based approach.
Java Programming: Leveraged Java’s built-in libraries for data manipulation and implemented custom algorithms for priority queues and hash tables.
Performance Testing and Graphing: Conducted experiments with different datasets and analyzed results using plotting tools to compare performance metrics.
Dynamic Resizing Techniques: Implemented dynamic resizing for hash tables to maintain performance.

How to Run
Heap Operations: Execute MergeMinPQ with different random seeds to test merging priority queues and validating sorted output.
Scheduler: Run HWScheduler with various input files specifying tasks and time commands to simulate the earliest deadline first scheduling.
Quadratic Probing Hash Table: Use QuadraticProbingHashST for inserting, deleting, and retrieving keys, and validate the table's resizing behavior.
Symbol Table Experiments: Execute HWSTCompare with different text files to benchmark symbol table operations, and use the generated data for performance analysis.
