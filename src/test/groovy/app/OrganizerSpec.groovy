package app

import spock.lang.Specification
import static org.hamcrest.MatcherAssert.assertThat
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest
import org.springframework.test.context.ContextConfiguration
import org.springframework.test.web.servlet.MockMvc
import org.springframework.test.web.servlet.ResultActions
import org.springframework.test.web.servlet.setup.MockMvcBuilders
import org.springframework.web.context.WebApplicationContext

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*

import static org.hamcrest.MatcherAssert.assertThat
import static org.hamcrest.Matchers.*

import spock.lang.*
import app.domain.Todo
import org.hamcrest.Matchers

import app.controller.DisplayTodoController
import app.controller.OrganizerController
import app.controller.TodoValidator

@ContextConfiguration
@WebMvcTest(controllers=[DisplayTodoController.class, OrganizerController.class, TodoValidator.class])
class OrganizerSpec extends Specification
{
	@Autowired
	private WebApplicationContext wac;
	
	private MockMvc mockMvc;
	private ResultActions result;
	
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
	
	/*
	Given the context of the controller is setup
	When I perform an HTTP GET /
	Then the status of the HTTP response should be 302
	And I should be redirected to URL /list
	 */
	def "1: get / has response 302 and redirect" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a get /"
			result = mockMvc.perform(get('/'))
		then: "the status of the HTTP response should be 302"
			result.andExpect(status().is(302))
		and: "I should be redirected to /list"
			result.andExpect(redirectedUrl('/list'))
	}
	
	/*
	Given the context of the controller is setup
	And the organizer has no todos (use OrganizerApp.organizer.todos = new ArrayList())
	When I perform an HTTP GET /create
	Then the status of the HTTP response should be Ok (200)
	And I should see the view CreateTodo
	And the model attribute todo has property description with value null
	 */
	def "2: get /create with no todos shows view CreateTodo with attribute todo" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		and: "the organizer has no todos"
			OrganizerApp.organizer.todos = new ArrayList()
		when: "I perform a get /create"
			result = mockMvc.perform(get('/create'))
		then: "the status of the HTTP response should be OK (200)"
			result.andExpect(status().is(200))
		and: "I should see view CreateTodo"
			result.andExpect(view().name('CreateTodo'))
		and: "the model attribute todo has property description with value null"
			result.andExpect(model().attribute('description', is(null)))
	}
	
	/*
	Given the context of the controller is setup
	And the organizer has no todos (use OrganizerApp.organizer.todos = new ArrayList())
	When I perform an HTTP GET /list
	Then the status of the HTTP response should be Ok (200)
	And I should see the view NoTodo
	 */
	def "3: get /list with no todos shows view NoTodo with response 200" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		and: "the organizer has no todos"
			OrganizerApp.organizer.todos = new ArrayList()
		when: "I perform a get /list"
			result = mockMvc.perform(get('/list'))
		then: "the status of the HTTP response should be OK (200)"
			result.andExpect(status().is(200))
		and: "I should see view NoTodo"
			result.andExpect(view().name('NoTodo'))
	}
	
	/*
	Given the context of the controller is setup
	And the organizer has todos
	When I perform an HTTP GET /next
	Then the status of the HTTP response should be Ok (200)
	And I should see the view NextTodo
	 */
	def "4: get /next with todos shows view NextTodo with response 200" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		and: "the organizer has todos"
			OrganizerApp.organizer.addTodo(new Todo())
		when: "I perform a get /next"
			result = mockMvc.perform(get('/next'))
		then: "the status of the HTTP response should be OK (200)"
			result.andExpect(status().is(200))
		and: "I should see view NextTodo"
			result.andExpect(view().name('NextTodo'))
	}
	
	/*
	Given the context of the controller is setup
	When I perform an HTTP POST /create with

	task = 'my Task'
	description = 'my Description'
	priority = '0'
	cancel = ''
	
	Then the status of the HTTP response should be 302
	And I should be redirected to URL /list
	 */
	def "5: post /create with parameters redirects to /list with response 302" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', 'my Task')
				.param('description', 'my Description')
				.param('priority', '0')
				.param('cancel', ''))
		then: "I should get the HTTP response 302"
			result.andExpect(status().is(302))
		and: "I should be redirected to URL /list"
			result.andExpect(redirectedUrl("/list"))
	}
	
	/*
	Given the context of the controller is setup
	When I perform an HTTP POST /create with

	task = ''
	description = 'my Description'
	priority = '4'
	important = '1'
	add = ''
	
	Then the status of the HTTP response should be Ok (200)
	And I should see the view CreateTodo
	And the model attribute todo has property priority equal to 4

	Make sure to use hasProperty of Hamcrest, e.g., by importing import static org.hamcrest.Matchers.*
	(there is a name clash with hasProperty of Groovy).
	 */
	def "6: post /create with parameters shows view CreateTodo with priority 4" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', '')
				.param('description', 'my Description')
				.param('priority', '4')
				.param('important', '1')
				.param('add', ''))
		then: "I should get the HTTP response 200"
			result.andExpect(status().is(200))
		and: "I should see the view CreateTodo"
			result.andExpect(view().name('CreateTodo'))
		and: "the model attribute todo has property priority with value 4"
			result.andExpect(model().attribute('todo', hasProperty('priority', is(4))))
	}
	
	/*
	Whenever the organizer has no todos, the HTTP GET request /next should show the view NoTodo
	 */
	def "7: get /next with no todos shows view NoTodo" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		and: "the organizer has no todos"
			OrganizerApp.organizer.todos = new ArrayList();
		when: "I perform a get /next"
			result = mockMvc.perform(get('/next'))
		then: "I should see view NoTodo"
			result.andExpect(view().name('NoTodo'))
	}
	
	/*
	Whenever the organizer has todos, the HTTP GET request /create should show the view CreateTodo
	 */
	def "8: get /create with todos shows view CreateTodo" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		and: "the organizer has todos"
			OrganizerApp.organizer.addTodo(new Todo())
		when: "I perform a get /create"
			result = mockMvc.perform(get('/create'))
		then: "I should see view CreateTodo"
			result.andExpect(view().name('CreateTodo'))
	}
	
	/*
	The HTTP POST request /create with the values listed below should redirect to URL /list:

	task = ''
	description = 'my Description'
	priority = '0'
	cancel = ''
	 */
	def "9: post /create with parameters redirects to /list" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', '')
				.param('description', 'my Description')
				.param('priority', '0')
				.param('cancel', ''))
		then: "I should be redirected to URL /list"
			result.andExpect(redirectedUrl("/list"))
	}
	
	/*
	The HTTP POST request /create with the values listed below should show the view CreateTodo
	and the model attribute todo should have errors:

	task = ''
	description = ''
	priority = '0'
	important = '1'
	add = ''
	 */
	def "10: post /create with parameters shows view CreateTodo with errors in attribute todo" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', '')
				.param('description', '')
				.param('priority', '0')
				.param('important', '1')
				.param('add', ''))
		then: "I should see the view CreateTodo"
			result.andExpect(view().name('CreateTodo'))
		and: "The model attribute todo has errors"
		result.andExpect(model().attributeHasErrors('todo'))
	}
	
	/*
	Complete the feature specification in the class OrganizerSpec with additional feature methods
	in order to achieve instructions coverage and branch coverage of ShowTodoController,
	OrganizerController, and TodoValidator.

	That is, implement as many feature methods as necessary in order to achieve 100% instructions
	coverage and branch coverage of ShowTodoController, OrganizerController, and TodoValidator.

	There is no limit in the number of test cases to be developed but there is a point where adding
	more test cases is pointless (and it is your job, as a tester, to find what this upper bound
	may be). However, please do not try with more than 20 test cases.

	We are going to use Jacoco for computing code coverage. You can see the instructions coverage
	and branch coverage by running ./gradlew clean test (or gradlew clean test for MS Windows)
	and inspecting the Jacoco report at build/reports/jacoco/test/html/index.html.
	 */
	
	def "ext-1: post /create with parameters (including add) gives errors" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', '')
				.param('description', 'a description')
				.param('priority', '101')
				.param('important', '0')
				.param('add', ''))
		then: "I should see the view CreateTodo"
			result.andExpect(view().name('CreateTodo'))
		and: "The model attribute todo has errors"
		result.andExpect(model().attributeHasErrors('todo'))
	}
	
	def "ext-2: post /create with parameters (including add) gives errors" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', '')
				.param('description', 'a verrrrrrrrry loooooong description (>20 chars)')
				.param('priority', '99')
				.param('important', '0')
				.param('add', ''))
		then: "I should see the view CreateTodo"
			result.andExpect(view().name('CreateTodo'))
		and: "The model attribute todo has errors"
		result.andExpect(model().attributeHasErrors('todo'))
	}
	
	def "ext-3: post /create with parameters (including add) gives errors" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', '')
				.param('description', 'short description')
				.param('priority', '99')
				.param('important', '0')
				.param('add', ''))
		then: "I should see the view CreateTodo"
			result.andExpect(view().name('CreateTodo'))
		and: "The model attribute todo has errors"
		result.andExpect(model().attributeHasErrors('todo'))
	}
	
	def "ext-4: post /create with parameters (including add) gives errors" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', 'short description')
				.param('description', 'short description')
				.param('priority', '99')
				.param('important', '1')
				.param('add', ''))
		then: "I should see the view CreateTodo"
			result.andExpect(view().name('CreateTodo'))
		and: "The model attribute todo has errors"
		result.andExpect(model().attributeHasErrors('todo'))
	}
	
	//100% branch coverage achieved on TodoValidator
	//now onto DisplayTodoController
	
	def "ext-5: get /list" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /list with parameters"
			result = mockMvc.perform(get('/list'))
		then: "I should see the view ListTodos"
			result.andExpect(view().name('ListTodos'))
	}
	
	//100% branch coverage achieved on DisplayTodoController
	//now onto OrganizerController
	
	def "ext-6: post /create with param add and no errors redirects to /list" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a post /create with parameters"
			result = mockMvc.perform(post('/create')
				.param('task', 'task')
				.param('description', 'description')
				.param('priority', '99')
				.param('important', '1')
				.param('add', ''))
		then: "I should be redirected to /list"
			result.andExpect(redirectedUrl('/list'))
	}
	
	def "ext-7: get /delete with param id redirects to /list" ()
	{
		given: "the context of the controller is setup"
			mockMvc = MockMvcBuilders.webAppContextSetup(this.wac).build()
		when: "I perform a get /delete with parameters (including id)"
			result = mockMvc.perform(get('/delete')
				.param('id','0'))
		then: "I should be redirected to /list"
			result.andExpect(redirectedUrl('/list'))
	}
}