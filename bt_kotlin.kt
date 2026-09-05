fun tongdiem(a: Float,b: Float,c: Float):Float
{
    return a+b+c
}
fun diemtrungbinh(a: Float,b: Float,c: Float):Float
{
    return (a+b+c)/3
}
fun diemcaonhat(a: Float,b: Float,c: Float):Float
{
    if(a>=b)
        if(a>=c) return a
        else return c
    else
        if(b>=c) return b
        else return c
}
fun kiemtragpa(a: Float,b: Float,c: Float):Boolean
{
    if (diemtrungbinh(a,b,c)>=5.0) return true
    else return false
}
fun main() {
    println("Họ và tên: Nguyễn Quốc Trung")
    println("Mã sinh viên: 2415053122246")
    println("Nhập điểm math: ")
    val math = readln().toFloat()
    println("Nhập điểm programming: ")
    val programming = readln().toFloat()
    println("Nhập điểm database: ")
    val database = readln().toFloat()
    println("Tổng điểm: "+tongdiem(math,programming,database))
    println("Điểm trung bình: "+diemtrungbinh(math,programming,database))
    println("Điểm cao nhất "+diemcaonhat(math,programming, database))
    if(kiemtragpa(math,programming,database)==true)
        println("Sinh viên đạt tiêu chí")
    else
        println("Sinh viên không đạt yêu cầu")

}