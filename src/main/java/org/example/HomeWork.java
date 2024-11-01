package org.example;


import java.util.ArrayList;
import java.util.List;
import java.util.stream.IntStream;

public class HomeWork {

    /**
     * <h1>Задание 1.</h1>
     * Решить задачу
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1439">https://acm.timus.ru/problem.aspx?space=1&num=1439</a>
     */
    public List<Integer> getOriginalDoorNumbers(int maxDoors, List<Action> actionList) {
        var result = new ArrayList<Integer>();
        var doorsTreap = getTreap(maxDoors);
        var removed = 0;
        for (Action action : actionList) {
            var node = doorsTreap
                    .getSubSet(removed + action.doorNumber--)
                    .get(action.doorNumber);
            if (action.isLook())
                result.add(node.getKey());
            else {
                doorsTreap.remove(node.getKey());
                removed++;
            }
        }

        return result;
    }

    /**
     * <h1>Задание 2.</h1>
     * Решить задачу <br/>
     * <a href="https://acm.timus.ru/problem.aspx?space=1&num=1521">https://acm.timus.ru/problem.aspx?space=1&num=1521</a><br/>
     * <h2>Пошагово</h2>
     * Для 5 3 входных данных:<br/>
     * _ -> 3 позиции<br/>
     * _ 1 2 <b>3</b> 4 5 => 3 <br/>
     * <b>1</b> 2 _ 4 5 => 1 <br/>
     * _ 2 4 <b>5</b> => 5 <br/>
     * <b>2</b> 4 _ => 2 <br/>
     * _ <b>4</b> => 4
     */
    public List<Integer> getLeaveOrder(int maxUnits, int leaveInterval) {
        var solders = getTreap(maxUnits);
        var order = leaveInterval - 1;
        var result = new ArrayList<Integer>();
        for (int i = 0; i < maxUnits; i++) {
            var inorder = solders.inorder();
            order = order % inorder.size();
            result.add(inorder.get(order));
            solders.remove(inorder.get(order));
            order += leaveInterval - 1;
        }
        return result;
    }

    private Treap<Integer> getTreap(int maxValue) {
        var treap = new Treap<Integer>();
        IntStream.rangeClosed(1, maxValue).forEach(treap::add);
        return treap;
    }

}
