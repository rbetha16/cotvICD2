On Error Resume Next

Set xlApp = CreateObject("Excel.Application")
xlApp.DisplayAlerts = False

ExcelPath = WScript.Arguments(0)

Set xlBook = xlApp.Workbooks.Open(ExcelPath)
xlApp.Run "sendMail"
xlApp.Quit

Set xlBook = Nothing
Set xlApp = Nothing