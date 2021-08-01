package com.ecomm.sciproapplication.utils

import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.database.Cursor
import android.graphics.Canvas
import android.net.Uri
import android.os.Build
import android.provider.MediaStore
import android.text.*
import android.view.View
import android.widget.EditText
import androidx.annotation.RequiresApi
import androidx.core.graphics.withTranslation
import com.ecomm.sciproapplication.R
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.snackbar.Snackbar
import java.text.SimpleDateFormat
import java.util.*
import kotlin.math.round


private const val TAG = "Extentions"

/**
 * to format date to a specific format like have only the 3 first letters of a month to have custom date
 * @param date the date to format
 * @param format the format of date that who want
 * @return the date formatted
 */
fun Date.convertDateToSpecificStringFormat(format: String = "dd MMM yyyy kk:mm"): String {
    val dateFormat = SimpleDateFormat(format, Locale.getDefault())
    return dateFormat.format(this)
}

/**
 * to convert date to specific format
 * this function put Today instead of the date with the hh:mm if is the date of today and Yesterday if it yesterday
 * In addition this function is able to add the day of week in the date
 * @param context the context to get the string in the resources
 * @param format the format of the date if it is not today or yesterday
 * @param isDayOfWeek to tell if you want the day of week or not. the default value is false
 */
fun Date.convertDateToASpecificFormatWithTodayAndYesterdayInCount(
    context: Context,
    format: String = "dd MMM yyyy kk:mm",
    isDayOfWeek: Boolean = false
): String {
    val now = Calendar.getInstance()
    val date = Calendar.getInstance()
    date.time = this

    when {
        now.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                && now.get(Calendar.DAY_OF_YEAR) == date.get(Calendar.DAY_OF_YEAR) -> {
            return context.getString(R.string.today_str) + " " + this.convertDateToSpecificStringFormat(
                "kk:mm"
            )
        }
        now.get(Calendar.YEAR) == date.get(Calendar.YEAR)
                && now.get(Calendar.DAY_OF_YEAR) - date.get(Calendar.DAY_OF_YEAR) == 1 -> {
            return context.getString(R.string.yesterday_str) + " " + this.convertDateToSpecificStringFormat(
                "kk:mm"
            )
        }
        isDayOfWeek -> {
            return if (this.getDayOfWeekFromDate().length > 3) {
                this.getDayOfWeekFromDate().substring(
                    0,
                    3
                ) + ", " + this.convertDateToSpecificStringFormat(format)
            } else {
                this.getDayOfWeekFromDate() + " " + this.convertDateToSpecificStringFormat(format)
            }
        }
        else -> {
            return this.convertDateToSpecificStringFormat(format)
        }
    }
}

/**
 * to get a year form a date because date property year is deprecated
 * @param date the date to have the year
 * @return the number of the year
 */
fun Date.getYearFromDate(): Int {
    val calendar = GregorianCalendar()
    calendar.time = this
    return calendar.get(Calendar.YEAR)
}

/**
 * to get the month from a date because the property month of the class date is deprecated
 * @param date the date to have the month
 * @return the number of the month
 */
fun Date.getMonthFromDate(): Int {
    val calendar = GregorianCalendar()
    calendar.time = this
    return calendar.get(Calendar.MONTH)
}

/**
 * to get the day of the month from a date
 */
fun Date.getDayOfMonthFromDate(): Int {
    val calendar = GregorianCalendar()
    calendar.time = this
    return calendar.get(Calendar.DAY_OF_MONTH)
}

fun Date.getDayOfWeekFromDate(): String {
    val calendar = GregorianCalendar()
    calendar.time = this
    return calendar.getDisplayName(Calendar.DAY_OF_WEEK, Calendar.LONG, Locale.getDefault())
}

fun Date.getLastDayOfMonth(): Date {
    val calendar = Calendar.getInstance()
    calendar.time = this
    calendar.add(Calendar.MONTH, 1)
    calendar[Calendar.DAY_OF_MONTH] = 1
    calendar.add(Calendar.DATE, -1)
    val lastDayOfMonth = calendar.time
    return lastDayOfMonth
}

/**
 * Permet de formater la date et renvoie avec le mot
 * toDay(ayjourdhui si la date correspond à celle
 * du jours courant
 * **/
fun Date.formatDateWithToDay(context: Context, fromat: String = "dd MMM yyy"): String {
    val todayDate = this.convertDateToSpecificStringFormat("dd MMM yyy")
    val sendDate = this.convertDateToSpecificStringFormat("dd MMM yyy")
    if (todayDate == sendDate) {
        return "${context.getString(R.string.today_str)}: ${
            this.convertDateToSpecificStringFormat(
                "kk:mm"
            )
        }"
    }
    return this.convertDateToSpecificStringFormat(fromat)
}

fun Uri.getRealPathFromUri(
    context: Context
): String? {
    var cursor: Cursor? = null
    return try {
        val proj = arrayOf(MediaStore.Images.Media.DATA)
        cursor = context.contentResolver.query(this, proj, null, null, null)
        val column_index: Int = cursor!!.getColumnIndexOrThrow(MediaStore.Images.Media.DATA)
        cursor.moveToFirst()
        cursor.getString(column_index)
    } finally {
        cursor?.close()
    }
}


fun View.showSnackBar(text: String, duration: Int = Snackbar.LENGTH_LONG) {
    Snackbar.make(this, text, duration).show()
}

fun EditText.setReadOnly(value: Boolean, inputType: Int = InputType.TYPE_NULL) {
    isFocusable = !value
    isFocusableInTouchMode = !value
    this.inputType = inputType
}

fun Date.addDtates(number: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.DAY_OF_MONTH, number)
    return cal.time
}

fun StaticLayout.draw(canvas: Canvas, x: Float, y: Float) {
    canvas.withTranslation(x, y) {
        draw(this)
    }
}

fun Date.addMonthOnDate(month: Int): Date {
    val cal = Calendar.getInstance()
    cal.time = this
    cal.add(Calendar.MONTH, month)
    return cal.time
}

fun Date.addWeekToDate(nomberOfWeek: Int): Date {
    val c = Calendar.getInstance()
    c.time = this
    c.add(Calendar.WEEK_OF_MONTH, nomberOfWeek)
    return c.time
}

@RequiresApi(Build.VERSION_CODES.M)
@SuppressLint("WrongConstant")

fun Canvas.drawMultilineText(
    text: CharSequence,
    textPaint: TextPaint,
    width: Int,
    x: Float,
    y: Float,
    start: Int = 0,
    end: Int = text.length,
    alignment: Layout.Alignment = Layout.Alignment.ALIGN_NORMAL,
    textDir: TextDirectionHeuristic = TextDirectionHeuristics.LTR,
    spacingMult: Float = 1f,
    spacingAdd: Float = 0f,
    hyphenationFrequency: Int = Layout.HYPHENATION_FREQUENCY_NONE,
    justificationMode: Int = Layout.JUSTIFICATION_MODE_NONE
) {

    val staticLayout = StaticLayout.Builder
        .obtain(text, start, end, textPaint, width)
        .setAlignment(alignment)
        .setTextDirection(textDir)
        .setLineSpacing(spacingAdd, spacingMult)
        .setBreakStrategy(1)
//        .setJustificationMode(justificationMode)
        .build()

    staticLayout.draw(this, x, y)
}


fun Double.round(decimals: Int): Double {
    var multiplier = 1.0
    repeat(decimals) {
        multiplier *= 10
    }
    return round(this * multiplier) / multiplier
}


fun Double.removeZeroAtEnd(): String {
    val valueStr = "" + (this)
    val amountTab = (valueStr).split(".")
    return try {
        if (amountTab.size == 2 && amountTab.last().equals("0", true)) {
            amountTab[0]
        } else {
            valueStr
        }
    } catch (e: Exception) {
        valueStr
    }
}


fun Date.getDelaitByValue(hour: Int): Long {
    val hourInMilis: Long = (hour * 60 * 60 * 1000).toLong()
    return (this.time + hourInMilis)
}


fun Long.getMinuteLeftOfTime(): String? {
    return try {
        val leftTime: Long = this - Calendar.getInstance().time.time
        val cal = Calendar.getInstance().apply {
            timeInMillis = leftTime
        }
        cal.get(Calendar.MINUTE).toString()
    } catch (e: Exception) {
        e.printStackTrace()
        null
    }

}


fun String.removeSpaces(): String {
    return this.replace("\\s".toRegex(), "")
}

fun String.cleanPhoneNumber(): String {
    return this.removeSpaces().replace("(", "").replace(")", "").replace("+", "")
        .removePrefix("237")
}

// Afin d'afficher uniquement la premier et la dernière chaine de caractère du nom
fun String?.splitFirstAndSecondName(): String {
    val arrayName = this?.split(" ")
    var nameToShow = this ?: ""
    if (arrayName?.size ?: 0 > 1) {
        nameToShow = arrayName?.first() + " " + (arrayName?.get(1) ?: "")
    }
    return nameToShow
}

fun Int.getLimiteDateByPreference(): Long {
    val timeNow = Calendar.getInstance().timeInMillis
    val oneDayMillis: Long = 24 * 60 * 60 * 1000
    return when (this) {
        1 -> {
            //weekly
            timeNow - (oneDayMillis * 7)
        }
        2 -> {
            //monthly
            timeNow - (oneDayMillis * 30)
        }
        3 -> {
            //trimester
            timeNow - (oneDayMillis * 30 * 3)
        }
        4 -> {
            //semester
            timeNow - (oneDayMillis * 30 * 6)
        }
        5 -> {
            //annual
            timeNow - (oneDayMillis * 30 * 12)
        }
        else -> {
            //all
            0
        }
    }
}


fun String.isNumericalValue(): Boolean {
    return try {
        this.toLong()
        true
    } catch (e: NumberFormatException) {
        false
    }
}


fun String.getMiniNameOfPeriode(): String {
    if (this.isNotBlank() && this.length > 3) {
        when {
            (this[0].toString().isNumericalValue() && this[1].toString().isNumericalValue()) -> {
                return this.removeSpaces().substring(0, 3)
            }
            (this[0].toString().isNumericalValue() && !(this[1].toString().isNumericalValue())) -> {
                return this.removeSpaces().substring(0, 2)
            }
            (!this[0].toString().isNumericalValue()) -> {
                return this.first().toString().toUpperCase(Locale.ROOT)
            }
        }
    } else {
        return this
    }
    return this
}

fun Context.showTexteForStartAndEndDate(startDate: Date, endDate: Date): String {
    //val to = this.getString(R.string.to_au)
    return "${startDate.convertDateToSpecificStringFormat("dd MMM yyyy")}  -  ${
        endDate.convertDateToSpecificStringFormat(
            "dd MMM yyyy"
        )
    }".capitalize()
}

fun BottomSheetBehavior<*>.showBottomSheet() {
    try {
        this.state = BottomSheetBehavior.STATE_EXPANDED
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun BottomSheetBehavior<*>.hideBottomSheet() {
    try {
        this.state = BottomSheetBehavior.STATE_COLLAPSED
    } catch (e: Exception) {
        e.printStackTrace()
    }
}

fun Context.makeCall(number: String): Boolean {
    return try {
        val intent = Intent(Intent.ACTION_CALL, Uri.parse("tel:$number"))
        startActivity(intent)
        true
    } catch (e: Exception) {
        e.printStackTrace()
        false
    }
}


