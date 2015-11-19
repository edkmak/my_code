Homework #3 Write-Up
1)BinaryHeap:isEmpty O(1) , size O(1), insert O(log2N), findMin O(1), and deleteMin O(log2N)
  ThreeHeap:isEmptyO(1), size O(1), insert O(log3N), findMinO(1), and deleteMin(Olog3N)
  MyPQ(Unsorted Array):isEmptyO(1), sizeO(1), insert O(1), findMin O(N), and deleteMin O(N)
  
2)

3) a)The Asymptopic analysis was prety useful because the measured analysis what I expected.
   b)The analysis matched what I expected for the most part.
   c)I would recommend someone to use the BinaryHeap over the other two options. The unsorted list PQ
     has an insert of O(1) and a delete of O(N), which makes insert/delete O(N). //ThreeHeap on
     on the other hand has more comparisions needed to comparethe childrne. However, binary heaps
     only need two comparisons.
     
4) I tested my priority queue's insert() and deleteMin() methods. I did this by creating a new 
   array, an then generating random doubles to insert into the priority queues. After inserting 
   a number of times, I called deleteMin() on the priority queue until it was empty. 




How useful was the asymptotic analysis for predicting the measured run time of insert and deleteMin for your three implementations?
If your predictions differed substantially from your measured times, speculate as to why this occurred.
Which of your three implementations would you recommend to someone who needs to use a heap? Why is that one preferred? Are there any conditions under which you might suggest using your other implementations?