package franug.oompaloompatest.model

data class ApiResponse(
    val current: Int,
    val total: Int,
    val results: Array<OompaLoompa>
)
