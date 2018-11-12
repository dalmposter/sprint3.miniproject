package app

import spock.lang.Specification
import static org.hamcrest.MatcherAssert.assertThat

import app.domain.Todo
import org.hamcrest.Matchers

class OrganizerSpec extends Specification
{
	//checkpoint
	def "0: decreasing priority throws RuntimeException" ()
	{
		given: "a todo with priority 10"
			def t = new Todo("task", "task for 0")
			t.setPriority(10)
		when: "I set the priority of the todo to 9"
			t.setPriority(9)
		then: "a RuntimeException is thrown"
			thrown(RuntimeException.class)
	}
}
