package co.condorlabs.coologger.events

sealed class WidgetType(val name: String) : CharSequence {

    object Button : WidgetType(BUTTON_TYPE_NAME)
    object ImageView : WidgetType(IMAGE_VIEW_TYPE_NAME)

    override fun get(index: Int): Char = name[index]
    override val length: Int = name.length
    override fun toString(): String = name
    override fun subSequence(startIndex: Int, endIndex: Int): CharSequence =
        name.subSequence(startIndex, endIndex)

    companion object {
        fun createFromString(type: String) = when (type) {
            BUTTON_TYPE_NAME -> Button
            IMAGE_VIEW_TYPE_NAME -> ImageView
            else -> throw IllegalArgumentException("$type does not exist")
        }
    }
}

const val BUTTON_TYPE_NAME = "BUTTON"
const val IMAGE_VIEW_TYPE_NAME = "IMAGE_VIEW"
