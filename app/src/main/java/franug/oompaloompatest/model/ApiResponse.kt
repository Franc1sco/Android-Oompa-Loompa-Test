package franug.oompaloompatest.model

data class ApiResponse(
    val current: Int,
    val total: Int,
    val results: Array<OompaLoompa>? = null
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as ApiResponse

        if (current != other.current) return false
        if (total != other.total) return false
        if (results != null) {
            if (other.results == null) return false
            if (!results.contentEquals(other.results)) return false
        } else if (other.results != null) return false

        return true
    }

    override fun hashCode(): Int {
        var result = current
        result = 31 * result + total
        result = 31 * result + (results?.contentHashCode() ?: 0)
        return result
    }
}
