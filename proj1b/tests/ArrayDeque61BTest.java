import deque.ArrayDeque61B;

import deque.Deque61B;
import jh61b.utils.Reflection;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.lang.reflect.Field;
import java.util.List;

import static com.google.common.truth.Truth.assertThat;
import static com.google.common.truth.Truth.assertWithMessage;

public class ArrayDeque61BTest {

//     @Test
//     @DisplayName("ArrayDeque61B has no fields besides backing array and primitives")
//     void noNonTrivialFields() {
//         List<Field> badFields = Reflection.getFields(ArrayDeque61B.class)
//                 .filter(f -> !(f.getType().isPrimitive() || f.getType().equals(Object[].class) || f.isSynthetic()))
//                 .toList();
//
//         assertWithMessage("Found fields that are not array or primitives").that(badFields).isEmpty();
//     }
    @Test
    /** a test for resize method. */
    public void resizeTest(){
        ArrayDeque61B<Integer> arr=new ArrayDeque61B<>();

        arr.addFirst(2);
        arr.addLast(3);
        arr.addLast(4);
        arr.addFirst(4);
        arr.addFirst(2);
        arr.addLast(3);
        arr.addLast(4);
        arr.addFirst(4);

        assertThat(arr.size()).isEqualTo(8);

        arr.addLast(1);
        assertThat(arr.size()).isEqualTo(9);
        assertThat(arr.alength()).isEqualTo(16);

        arr.addFirst(2);
        arr.addLast(3);
        arr.addLast(4);
        arr.addFirst(4);
        arr.addFirst(2);
        arr.addLast(3);
        arr.addLast(4);
        arr.addFirst(4);

        assertThat(arr.size()).isEqualTo(17);
        assertThat(arr.alength()).isEqualTo(32);

        arr.removeFirst();
        arr.removeLast();
        arr.removeLast();
        arr.removeFirst();
        arr.removeLast();
        arr.removeLast();
        arr.removeLast();
        arr.removeFirst();
        arr.removeLast();
        arr.removeLast();

        assertThat(arr.size()).isEqualTo(7);
        assertThat(arr.alength()).isEqualTo(16);
    }

    @Test
    /** a test for get method. */
    public void getTest(){
        Deque61B<Integer> arr=new ArrayDeque61B<>();

        arr.addFirst(2);
        arr.addLast(3);
        arr.addLast(4);

        assertThat(arr.get(-1)).isNull();
        assertThat(arr.get(2341)).isNull();
        assertThat(arr.get(1)).isEqualTo(3);
    }

    @Test
    /** a test for isEmptyAndsize method. */
    public void isEmptyAndsizeTest(){
        Deque61B<Integer> arr=new ArrayDeque61B<>();
        assertThat(arr.isEmpty()).isTrue();

        arr.addFirst(2);
        arr.addLast(3);
        arr.addLast(4);
        assertThat(arr.size()).isEqualTo(3);
    }

    @Test
    /** a test for toString method. */
    public void toStringTest(){
        Deque61B<Integer> arr=new ArrayDeque61B<>();

        arr.addFirst(2);
        arr.addLast(3);
        arr.addLast(4);

        assertThat(arr.toList()).containsExactly(2,3,4).inOrder();
    }

    @Test
    /** a test for rmFirstAndLast method. */
    public void rmFirstAndLastTest(){
        Deque61B<Integer> arr=new ArrayDeque61B<>();

        assertThat(arr.toList()).isNull();

        arr.addFirst(2);
        arr.addLast(3);
        arr.addLast(4);

        assertThat(arr.removeFirst()).isEqualTo(2);
        assertThat(arr.removeLast()).isEqualTo(4);
    }

}
