/*
Design and implement a data structure for Least Recently Used (LRU) cache. It should support the following operations: get and set.

get(key) - Get the value (will always be positive) of the key if the key exists in the cache, otherwise return -1.

set(key, value) - Set or insert the value if the key is not already present. When the cache reached its capacity, it should invalidate 
the least recently used item before inserting a new item.
*/

import java.util.ArrayList;
import java.util.HashMap;

class CacheNode {
    int key;
    int value;
    CacheNode prev;
    CacheNode next;
    
    public CacheNode(int key, int value) {
        this.key = key;
        this.value = value;
    }
}

class LRUCache {
    int capacity;
    HashMap<Integer, CacheNode> cache;
    CacheNode head, current;
    
    private CacheNode extractHead() {
        CacheNode extract = head;
        head = head.next;
        if(head != null) head.prev = null;
        else current = null;
        extract.next = null;
        return extract;
    }
    
    private void removeNode(CacheNode cnode) {
        if(cnode == head) {
            head = head.next;
            if(head != null) head.prev = null;
            else current = null;
        }
        else {
            cnode.prev.next = cnode.next;
            if(cnode.next != null) cnode.next.prev = cnode.prev;
            if(cnode == current) current = current.prev;
        }
        cnode.next = null;
    }
    
    private void addNode(CacheNode cnode) {
        if(head == null) {
            head = cnode;
            current = cnode;
        }
        else {
            current.next = cnode;
            cnode.prev = current;
            current = cnode;
        }
    }
    
    public LRUCache(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<Integer, CacheNode>();
    }
    
    public int get(int key) {
        int value;
        if(cache.containsKey(key)) {
            CacheNode cnode = cache.get(key);
            value = cnode.value;
            removeNode(cnode);
            addNode(cnode);
        }
        else {
            value = -1;
        }
        return value;
    }
    
    public void set(int key, int value) {
        if(cache.containsKey(key)) {
            CacheNode cnode = cache.get(key);
            cnode.value = value;
            cache.put(key, cnode);
            removeNode(cnode);
            addNode(cnode);
        }
        else if(cache.size() < capacity) {
            CacheNode cnode = new CacheNode(key, value);
            cache.put(key, cnode);
            addNode(cnode);
        }
        else {
            CacheNode rnode = extractHead();
            int rkey = rnode.key;
            cache.remove(rkey);
            CacheNode cnode = new CacheNode(key, value);
            cache.put(key, cnode);
            addNode(cnode);
        }
    }
}
/*
    Second Round
*/
class LRUCache2 {
    int capacity;
    HashMap<Integer, CacheNode> cache;
    CacheNode head, last;
    
    public LRUCache2(int capacity) {
        this.capacity = capacity;
        this.cache = new HashMap<Integer, CacheNode>();
    }
    
    private void disconnect(CacheNode node) {
        CacheNode prev = node.prev, next = node.next;
        // disconnect prev
        if(prev != null) {
            prev.next = next;
        }
        else {  // it is head
            head = next;
            if(head != null) head.prev = null;
        }
        // disconnect next
        if(next != null) {
            next.prev = prev;
        }
        else {  // it is last
            last = prev;
            if(last != null) last.next = null;
        }
        // clean node
        node.next = null;
        node.prev = null;
    }
    
    private void push(CacheNode node) {
        if(head != null) {
            node.next = head;
            head.prev = node;
        }
        else {
            last = node;
        }
        head = node;
    }
    
    private void add(CacheNode node) {
        cache.put(node.key, node);
        push(node);
    }
    
    private void remove() {
        if(last != null) {
            cache.remove(last.key);
            disconnect(last);
        }
    }
    
    public int get(int key) {
        int value = -1;
        if(cache.containsKey(key)) {   // it is in the cache
            CacheNode node = cache.get(key);
            value = node.value;
            // increase its priority
            disconnect(node);
            push(node);
        }
        return value;
    }
    
    public void set(int key, int value) {
        if(cache.containsKey(key)) {   // it is in the cache
            CacheNode node = cache.get(key);
            node.value = value;
            // increase its priority
            disconnect(node);
            push(node);
        }
        else {  // create a new one
            CacheNode node = new CacheNode(key, value);
            if(cache.size() == capacity) {  // size overflow
                remove();   // remove last one
            }
            add(node);
        }
    }
}

class Main {
    public static void main(String[] args) {
        LRUCache2 cache = new LRUCache2(105);
        System.out.println(processCache(cache));
    }

    public static ArrayList<Integer> processCache(LRUCache2 cache) {
        ArrayList<Integer> result = new ArrayList<Integer>();
        cache.set(33,219);
        result.add(cache.get(39));
        cache.set(96,56);
        result.add(cache.get(129));
        result.add(cache.get(115));
        result.add(cache.get(112));
        cache.set(3,280);
        result.add(cache.get(40));
        cache.set(85,193);
        cache.set(10,10);
        cache.set(100,136);
        cache.set(12,66);
        cache.set(81,261);
        cache.set(33,58);
        result.add(cache.get(3));
        cache.set(121,308);
        cache.set(129,263);
        result.add(cache.get(105));
        cache.set(104,38);
        cache.set(65,85);
        cache.set(3,141);
        cache.set(29,30);
        cache.set(80,191);
        cache.set(52,191);
        cache.set(8,300);
        result.add(cache.get(136));
        cache.set(48,261);
        cache.set(3,193);
        cache.set(133,193);
        cache.set(60,183);
        cache.set(128,148);
        cache.set(52,176);
        result.add(cache.get(48));
        cache.set(48,119);
        cache.set(10,241);
        result.add(cache.get(124));
        cache.set(130,127);
        result.add(cache.get(61));
        cache.set(124,27);
        result.add(cache.get(94));
        cache.set(29,304);
        cache.set(102,314);
        result.add(cache.get(110));
        cache.set(23,49);
        cache.set(134,12);
        cache.set(55,90);
        result.add(cache.get(14));
        result.add(cache.get(104));
        cache.set(77,165);
        cache.set(60,160);
        result.add(cache.get(117));
        cache.set(58,30);
        result.add(cache.get(54));
        result.add(cache.get(136));
        result.add(cache.get(128));
        result.add(cache.get(131));
        cache.set(48,114);
        result.add(cache.get(136));
        cache.set(46,51);
        cache.set(129,291);
        cache.set(96,207);
        result.add(cache.get(131));
        cache.set(89,153);
        cache.set(120,154);
        result.add(cache.get(111));
        result.add(cache.get(47));
        result.add(cache.get(5));
        cache.set(114,157);
        cache.set(57,82);
        cache.set(113,106);
        cache.set(74,208);
        result.add(cache.get(56));
        result.add(cache.get(59));
        result.add(cache.get(100));
        result.add(cache.get(132));
        cache.set(127,202);
        result.add(cache.get(75));
        cache.set(102,147);
        result.add(cache.get(37));
        cache.set(53,79);
        cache.set(119,220);
        result.add(cache.get(47));
        result.add(cache.get(101));
        result.add(cache.get(89));
        result.add(cache.get(20));
        result.add(cache.get(93));
        result.add(cache.get(7));
        cache.set(48,109);
        cache.set(71,146);
        result.add(cache.get(43));
        result.add(cache.get(122));
        cache.set(3,160);
        result.add(cache.get(17));
        cache.set(80,22);
        cache.set(80,272);
        result.add(cache.get(75));
        result.add(cache.get(117));
        cache.set(76,204);
        cache.set(74,141);
        cache.set(107,93);
        cache.set(34,280);
        cache.set(31,94);
        result.add(cache.get(132));
        cache.set(71,258);
        result.add(cache.get(61));
        result.add(cache.get(60));
        cache.set(69,272);
        result.add(cache.get(46));
        cache.set(42,264);
        cache.set(87,126);
        cache.set(107,236);
        cache.set(131,218);
        result.add(cache.get(79));
        cache.set(41,71);
        cache.set(94,111);
        cache.set(19,124);
        cache.set(52,70);
        result.add(cache.get(131));
        result.add(cache.get(103));
        result.add(cache.get(81));
        result.add(cache.get(126));
        cache.set(61,279);
        cache.set(37,100);
        result.add(cache.get(95));
        result.add(cache.get(54));
        cache.set(59,136);
        cache.set(101,219);
        cache.set(15,248);
        cache.set(37,91);
        cache.set(11,174);
        cache.set(99,65);
        cache.set(105,249);
        result.add(cache.get(85));
        cache.set(108,287);
        cache.set(96,4);
        result.add(cache.get(70));
        result.add(cache.get(24));
        cache.set(52,206);
        cache.set(59,306);
        cache.set(18,296);
        cache.set(79,95);
        cache.set(50,131);
        cache.set(3,161);
        cache.set(2,229);
        cache.set(39,183);
        cache.set(90,225);
        cache.set(75,23);
        cache.set(136,280);
        result.add(cache.get(119));
        cache.set(81,272);
        result.add(cache.get(106));
        result.add(cache.get(106));
        result.add(cache.get(70));
        cache.set(73,60);
        cache.set(19,250);
        cache.set(82,291);
        cache.set(117,53);
        cache.set(16,176);
        result.add(cache.get(40));
        cache.set(7,70);
        cache.set(135,212);
        result.add(cache.get(59));
        cache.set(81,201);
        cache.set(75,305);
        result.add(cache.get(101));
        cache.set(8,250);
        result.add(cache.get(38));
        cache.set(28,220);
        result.add(cache.get(21));
        cache.set(105,266);
        result.add(cache.get(105));
        result.add(cache.get(85));
        result.add(cache.get(55));
        result.add(cache.get(6));
        cache.set(78,83);
        result.add(cache.get(126));
        result.add(cache.get(102));
        result.add(cache.get(66));
        cache.set(61,42);
        cache.set(127,35);
        cache.set(117,105);
        result.add(cache.get(128));
        result.add(cache.get(102));
        result.add(cache.get(50));
        cache.set(24,133);
        cache.set(40,178);
        cache.set(78,157);
        cache.set(71,22);
        result.add(cache.get(25));
        result.add(cache.get(82));
        result.add(cache.get(129));
        cache.set(126,12);
        result.add(cache.get(45));
        result.add(cache.get(40));
        result.add(cache.get(86));
        result.add(cache.get(100));
        cache.set(30,110);
        result.add(cache.get(49));
        cache.set(47,185);
        cache.set(123,101);
        result.add(cache.get(102));
        result.add(cache.get(5));
        cache.set(40,267);
        cache.set(48,155);
        result.add(cache.get(108));
        result.add(cache.get(45));
        cache.set(14,182);
        cache.set(20,117);
        cache.set(43,124);
        result.add(cache.get(38));
        cache.set(77,158);
        result.add(cache.get(111));
        result.add(cache.get(39));
        cache.set(69,126);
        cache.set(113,199);
        cache.set(21,216);
        result.add(cache.get(11));
        cache.set(117,207);
        result.add(cache.get(30));
        cache.set(97,84);
        result.add(cache.get(109));
        cache.set(99,218);
        result.add(cache.get(109));
        cache.set(113,1);
        result.add(cache.get(62));
        cache.set(49,89);
        cache.set(53,311);
        result.add(cache.get(126));
        cache.set(32,153);
        cache.set(14,296);
        result.add(cache.get(22));
        cache.set(14,225);
        result.add(cache.get(49));
        result.add(cache.get(75));
        cache.set(61,241);
        result.add(cache.get(7));
        result.add(cache.get(6));
        result.add(cache.get(31));
        cache.set(75,15);
        result.add(cache.get(115));
        cache.set(84,181);
        cache.set(125,111);
        cache.set(105,94);
        cache.set(48,294);
        result.add(cache.get(106));
        result.add(cache.get(61));
        cache.set(53,190);
        result.add(cache.get(16));
        cache.set(12,252);
        result.add(cache.get(28));
        cache.set(111,122);
        result.add(cache.get(122));
        cache.set(10,21);
        result.add(cache.get(59));
        result.add(cache.get(72));
        result.add(cache.get(39));
        result.add(cache.get(6));
        result.add(cache.get(126));
        cache.set(131,177);
        cache.set(105,253);
        result.add(cache.get(26));
        cache.set(43,311);
        result.add(cache.get(79));
        cache.set(91,32);
        cache.set(7,141);
        result.add(cache.get(38));
        result.add(cache.get(13));
        cache.set(79,135);
        result.add(cache.get(43));
        result.add(cache.get(94));
        cache.set(80,182);
        result.add(cache.get(53));
        cache.set(120,309);
        cache.set(3,109);
        result.add(cache.get(97));
        cache.set(9,128);
        cache.set(114,121);
        result.add(cache.get(56));
        result.add(cache.get(56));
        cache.set(124,86);
        cache.set(34,145);
        result.add(cache.get(131));
        result.add(cache.get(78));
        cache.set(86,21);
        result.add(cache.get(98));
        cache.set(115,164);
        cache.set(47,225);
        result.add(cache.get(95));
        cache.set(89,55);
        cache.set(26,134);
        cache.set(8,15);
        result.add(cache.get(11));
        cache.set(84,276);
        cache.set(81,67);
        result.add(cache.get(46));
        result.add(cache.get(39));
        result.add(cache.get(92));
        result.add(cache.get(96));
        cache.set(89,51);
        cache.set(136,240);
        result.add(cache.get(45));
        result.add(cache.get(27));
        cache.set(24,209);
        cache.set(82,145);
        result.add(cache.get(10));
        cache.set(104,225);
        cache.set(120,203);
        cache.set(121,108);
        cache.set(11,47);
        result.add(cache.get(89));
        cache.set(80,66);
        result.add(cache.get(16));
        cache.set(95,101);
        result.add(cache.get(49));
        result.add(cache.get(1));
        cache.set(77,184);
        result.add(cache.get(27));
        cache.set(74,313);
        cache.set(14,118);
        result.add(cache.get(16));
        result.add(cache.get(74));
        cache.set(88,251);
        result.add(cache.get(124));
        cache.set(58,101);
        cache.set(42,81);
        result.add(cache.get(2));
        cache.set(133,101);
        result.add(cache.get(16));
        cache.set(1,254);
        cache.set(25,167);
        cache.set(53,56);
        cache.set(73,198);
        result.add(cache.get(48));
        result.add(cache.get(30));
        result.add(cache.get(95));
        cache.set(90,102);
        cache.set(92,56);
        cache.set(2,130);
        cache.set(52,11);
        result.add(cache.get(9));
        result.add(cache.get(23));
        cache.set(53,275);
        cache.set(23,258);
        result.add(cache.get(57));
        cache.set(136,183);
        cache.set(75,265);
        result.add(cache.get(85));
        cache.set(68,274);
        cache.set(15,255);
        result.add(cache.get(85));
        cache.set(33,314);
        cache.set(101,223);
        cache.set(39,248);
        cache.set(18,261);
        cache.set(37,160);
        result.add(cache.get(112));
        result.add(cache.get(65));
        cache.set(31,240);
        cache.set(40,295);
        cache.set(99,231);
        result.add(cache.get(123));
        cache.set(34,43);
        result.add(cache.get(87));
        result.add(cache.get(80));
        cache.set(47,279);
        cache.set(89,299);
        result.add(cache.get(72));
        cache.set(26,277);
        cache.set(92,13);
        cache.set(46,92);
        cache.set(67,163);
        cache.set(85,184);
        result.add(cache.get(38));
        cache.set(35,65);
        result.add(cache.get(70));
        result.add(cache.get(81));
        cache.set(40,65);
        result.add(cache.get(80));
        cache.set(80,23);
        cache.set(76,258);
        result.add(cache.get(69));
        result.add(cache.get(133));
        cache.set(123,196);
        cache.set(119,212);
        cache.set(13,150);
        cache.set(22,52);
        cache.set(20,105);
        cache.set(61,233);
        result.add(cache.get(97));
        cache.set(128,307);
        result.add(cache.get(85));
        result.add(cache.get(80));
        result.add(cache.get(73));
        result.add(cache.get(30));
        cache.set(46,44);
        result.add(cache.get(95));
        cache.set(121,211);
        cache.set(48,307);
        result.add(cache.get(2));
        cache.set(27,166);
        result.add(cache.get(50));
        cache.set(75,41);
        cache.set(101,105);
        result.add(cache.get(2));
        cache.set(110,121);
        cache.set(32,88);
        cache.set(75,84);
        cache.set(30,165);
        cache.set(41,142);
        cache.set(128,102);
        cache.set(105,90);
        cache.set(86,68);
        cache.set(13,292);
        cache.set(83,63);
        cache.set(5,239);
        result.add(cache.get(5));
        cache.set(68,204);
        result.add(cache.get(127));
        cache.set(42,137);
        result.add(cache.get(93));
        cache.set(90,258);
        cache.set(40,275);
        cache.set(7,96);
        result.add(cache.get(108));
        cache.set(104,91);
        result.add(cache.get(63));
        result.add(cache.get(31));
        cache.set(31,89);
        result.add(cache.get(74));
        result.add(cache.get(81));
        cache.set(126,148);
        result.add(cache.get(107));
        return result;
    }
}
