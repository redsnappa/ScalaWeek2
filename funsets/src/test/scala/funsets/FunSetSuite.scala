package funsets

import org.scalatest.FunSuite

import org.junit.runner.RunWith
import org.scalatest.junit.JUnitRunner

/**
 * This class is a test suite for the methods in object FunSets. To run
 * the test suite, you can either:
 *  - run the "test" command in the SBT console
 *  - right-click the file in eclipse and chose "Run As" - "JUnit Test"
 */
@RunWith(classOf[JUnitRunner])
class FunSetSuite extends FunSuite {


  /**
   * Link to the scaladoc - very clear and detailed tutorial of FunSuite
   *
   * http://doc.scalatest.org/1.9.1/index.html#org.scalatest.FunSuite
   *
   * Operators
   *  - test
   *  - ignore
   *  - pending
   */

  /**
   * Tests are written using the "test" operator and the "assert" method.
   */
  test("string take") {
    val message = "hello, world"
    assert(message.take(5) == "hello")
  }

  /**
   * For ScalaTest tests, there exists a special equality operator "===" that
   * can be used inside "assert". If the assertion fails, the two values will
   * be printed in the error message. Otherwise, when using "==", the test
   * error message will only say "assertion failed", without showing the values.
   *
   * Try it out! Change the values so that the assertion fails, and look at the
   * error message.
   */
  test("adding ints") {
    assert(1 + 2 === 3)
  }

  
  import FunSets._

  test("contains is implemented") {
    assert(contains(x => true, 100))
  }
  
  /**
   * When writing tests, one would often like to re-use certain values for multiple
   * tests. For instance, we would like to create an Int-set and have multiple test
   * about it.
   * 
   * Instead of copy-pasting the code for creating the set into every test, we can
   * store it in the test class using a val:
   * 
   *   val s1 = singletonSet(1)
   * 
   * However, what happens if the method "singletonSet" has a bug and crashes? Then
   * the test methods are not even executed, because creating an instance of the
   * test class fails!
   * 
   * Therefore, we put the shared values into a separate trait (traits are like
   * abstract classes), and create an instance inside each test method.
   * 
   */

  trait TestSets {
    val s1 = singletonSet(1)
    val s2 = singletonSet(2)
    val s3 = singletonSet(3)
    val s4 = singletonSet(3)

    val naturalNumberSet = (x:Int) => true
  }

  /**
   * This test is currently disabled (by using "ignore") because the method
   * "singletonSet" is not yet implemented and the test would fail.
   * 
   * Once you finish your implementation of "singletonSet", exchange the
   * function "ignore" by "test".
   */
  test("singletonSet(1) contains 1") {
    
    /**
     * We create a new instance of the "TestSets" trait, this gives us access
     * to the values "s1" to "s3". 
     */
    new TestSets {
      /**
       * The string argument of "assert" is a message that is printed in case
       * the test fails. This helps identifying which assertion failed.
       */
      assert(contains(s1, 1), "Singleton")
    }
  }

  test("union contains all elements") {
    new TestSets {
      val s = union(s1, s2)
      assert(contains(s, 1), "Union 1")
      assert(contains(s, 2), "Union 2")
      assert(!contains(s, 3), "Union 3")
    }
  }


  test("intersection returns all elements"){
    new TestSets{
      val s = intersect(s2,s3)
      assert(!contains(s,2), " 2 doesn't intersect")
      val t = intersect(s3,s4)
      assert(contains(t,3), "3 intersects" )


    }
  }


  test("difference returns whether they're in s and not t"){

    new TestSets{
      val s = diff(s2,s3)
      assert(contains(s,2), " 2 is in the difference")
      val t = diff(s3,s4)
      assert(!contains(t,3), "3 isn't in the difference" )

    }


  }

  test("given a set of odd and even numbers, for all, test if they are a set of odd numbers"){
    new TestSets {

      val falseTest = forall(naturalNumberSet,(x => x%2==1));
      assert(!falseTest, "All natural numbers dont satisfy p");

    }
  }

  test("given a set of odd and even numbers, for all, test if they are a set of even numbers"){
    new TestSets {

      val falseTest = forall(naturalNumberSet,(x => x%2==0));
      assert(!falseTest, "All natural numbers dont satisfy p");

    }
  }


  test("doubling all numbers should result in an even set"){
    new TestSets{

      val test1 = map(s1,(x:Int) => x * 2)
      val test2 = map(s2,(x:Int) => x * 2)
      val test3 = map(s3,(x:Int) => x * 2)

      val doubledNumbers = map(naturalNumberSet, (x:Int)=> x * 2);
      doubledNumbers.toString();
      val result = forall(doubledNumbers, x => x%2==0)
      assert(result, "All doubled numbers should return an even set!")

    }
  }

  test("given a set of even numbers, for all, test if they are a set of even numbers"){
    new TestSets {

      val positiveTest = forall((x => x%2==0),(x => x%2==0));
      assert(positiveTest, "All even numbers should satisfy p");

    }
  }

  test("give a set of natural numbers, there should exist odd numbers"){
    new TestSets {

      val positiveTest = exists(naturalNumberSet,(x => x%2==0));
      assert(positiveTest, "Natural numbers should satisfy p");

    }
  }

  test("give a set of odd numbers, there should be no even numbers"){
    new TestSets {

      val negativeTest = exists((x => x%2==1),(x => x%2==0));
      assert(!negativeTest, "Odd numbers should not satisfy p");

    }
  }


}
