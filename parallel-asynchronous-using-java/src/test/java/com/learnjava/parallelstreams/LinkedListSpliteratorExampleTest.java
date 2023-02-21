package com.learnjava.parallelstreams;

import com.learnjava.util.DataSet;
import org.junit.jupiter.api.RepeatedTest;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class LinkedListSpliteratorExampleTest {

    LinkedListSpliteratorExample arrayListSpliteratorExample = new LinkedListSpliteratorExample();

    @RepeatedTest(5)
    void arrayListSpliteratorExample(){
        //given
        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);
        //when
        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList,2,false);
        //then
        assertEquals(size,resultList.size());

    }

    @RepeatedTest(5)
    void arrayListSpliteratorExample_isParallel(){
        //given
        int size = 1000000;
        LinkedList<Integer> inputList = DataSet.generateIntegerLinkedList(size);
        //when
        List<Integer> resultList = arrayListSpliteratorExample.multiplyEachValue(inputList,2,true);
        //then
        assertEquals(size,resultList.size());

    }
}