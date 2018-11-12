"# popularmovies" 

Implemented latest Architecture with MVVM with ViewModel, kotlin, Koin, Couroutines,etc...
Added UseCases to manage all calls to the DB or to the Server, through the proper service and its repository.

This implementation follows this structure:
View - ViewModel - UseCaseWrapper - UseCase - Service - Repository - API / DB

All calls performed in viewModel uses an abstraction of the BaseUseCase to perform the "buildRepoCall()"
Through the TAG in each UseCase, the baseUseCase search and performs the call.
Reached this point, all the asynchronous task are performed in the BaseUseCase using Coroutines,
it performs a series of async blocks each on the proper thread, to generate a task that needs to be in
a different thread, and also another in the mainThread to be able to call back to the view the data or response
retrieved. The BaseUseCase manage through the couroutines the exceptionHandler which using kotlin blocks methodology
can answer back to the use-case and to the view in case of an error.

The BaseEntity follows a custom approach where the server always returns a result state, here does not make much more sense,
although I know, I have kept it to show my interest in following a good procedure to ensure that the response must have all the data
needed and a basic pattern to be used in the app.

Through Koin injection it's very easy to inject and generate the ViewModelFactory in each view, (transparent) and improves
the implementation and readability of the code.

All repositories and dataAccessObjects follow the interface - implementation pattern to be tested in the future if needed.
Also implemented a Empty state logic, and an animation to feed the recyclerView in each list.