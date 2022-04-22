package org.nice.soft.vocabulary.gui

class ListNode(var `val`: Int) {
    var next: ListNode? = null
}

class TreeNode(var `val`: Int) {
    var left: TreeNode? = null
    var right: TreeNode? = null
}

// "--X","X++"
fun main() {
    //println(getConcatenation(intArrayOf(1,2,3)).contentToString())
    val array = IntArray(10) { it }
    println(array.toSortedSet())
}

fun finalValueAfterOperations(operations: Array<String>): Int {
    return operations.asSequence().map { if (it[1] == '+') 1 else -1 }.sum()
}


