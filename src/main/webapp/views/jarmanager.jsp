<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="utf-8">
<meta http-equiv="X-UA-Compatible" content="IE=edge,Chrome=1" />
<meta name="viewport"
	content="width=device-width, initial-scale=1.0, maximum-scale=1.0">
<title>DMS..jar解析配置</title>
<script data-main="static/javascripts/views/jarmanager"
	src="static/javascripts/libs/requirejs-2.1.9/require.min.js"></script>
</head>
<body class="sticky-header">
	<jsp:include page="leftside.jsp"></jsp:include>
	<!-- main content start-->
	<div class="main-content" style="background-color: #eff0f4 !important;">
		<!-- header section start-->
		<div class="header-section">
			<!--toggle button start-->
			<a class="toggle-btn"> <i class="fa fa-bars"></i>
			</a>
			<!--toggle button end-->
		</div>
		<div class="wrapper" style="padding: 15px; height: 100%">
			<p>
				上传的jar的必须包含 String parseToCommon(InputStream is)的方法体<br />
				参数:java.io.InputStream<br /> 返回值:java.lang.String(JSON字符串)<br />
				如需进行空间查询,必须包含如下字段<br /> (上:)top<br /> (下:)bottom<br /> (左:)left<br />
				(右:)right<br />
			</p>
			<div class="container-fluid">
				<table id="configTable"
					class="table table-responsive table-condensed table-bordered table-striped">

				</table>
			</div>
			<div id="editPanel">
				<form id="jarForm" class="form-horizontal" method="post">
					<input class="hidden" name="pid" type="text">
					<div class="form-group">
						<label for="parsePatternInput" class="col-sm-2 control-label">表达式(正则)</label>
						<div class="col-xs-10">
							<textarea type="text" class="form-control required"
								placeholder="如:HJ1A-HSI-\d{6}-\D{3}-\d{2}-\d{2}-A\d-\d{8}-L2\d{1,}.XML"
								id="parsePatternInput" name="parsePattern" rows="5"></textarea>
						</div>
					</div>
					<div class="form-group">
						<label for="desInput" class="col-sm-2 control-label">说明</label>
						<div class="col-xs-10">
							<textarea type="text" class="form-control" id="desInput"
								placeholder="解析描述说明" name="des" rows="3"></textarea>
						</div>
					</div>
					<div class="form-group">
						<input class="hidden" name="fsId" type="text" id="fsInput">
						<label class="col-xs-2 control-label">jar文件</label>
						<div class="col-xs-10">
							<p id="chooseFlag" style="cursor: pointer; display: none;">
								<i class="fa fa-3x fa-file-zip-o"></i>
							</p>
							<button type="button" class="btn btn-info" id="chooseFileBtn">
								<i class="fa fa-file-o"></i> 选择文件
							</button>
						</div>
					</div>
					<div class="form-group">
						<div class="col-xs-10 col-xs-offset-2">
							<button id="submitBtn" type="button" class="btn btn-success">
								<i class="fa fa-file-o"></i> 提交
							</button>
							<button id="resetBtn" type="button" class="btn btn-warning">
								<i class="fa fa-undo"></i> 重置
							</button>
						</div>
					</div>
				</form>
			</div>
			<div>
				<table class="table table-bordered">
					<tbody>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">元字符</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">描述</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">\</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									将下一个字符标记符、或一个向后引用、或一个八进制转义符。例如，“\\n”匹配\n。“\n”匹配换行符。序列“\\”匹配“\”而“\(”则匹配“(”。即相当于多种编程语言中都有的“转义字符”的概念。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">^</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									匹配输入字符串的开始位置。如果设置了RegExp对象的Multiline属性，^也匹配“\n”或“\r”之后的位置。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">$</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									匹配输入字符串的结束位置。如果设置了RegExp对象的Multiline属性，$也匹配“\n”或“\r”之前的位置。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">*</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">匹配前面的子表达式任意次。例如，zo*能匹配“z”，也能匹配“zo”以及“zoo”。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">+</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									匹配前面的子表达式一次或多次(大于等于1次）。例如，“zo+”能匹配“zo”以及“zoo”，但不能匹配“z”。+等价于{1,}。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">?</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									匹配前面的子表达式零次或一次。例如，“do(es)?”可以匹配“do”或“does”中的“do”。?等价于{0,1}。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">{n}</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									n是一个非负整数。匹配确定的n次。例如，“o{2}”不能匹配“Bob”中的“o”，但是能匹配“food”中的两个o。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">{n,}</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									n是一个非负整数。至少匹配n次。例如，“o{2,}”不能匹配“Bob”中的“o”，但能匹配“foooood”中的所有o。“o{1,}”等价于“o+”。“o{0,}”则等价于“o*”。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">{n,m}</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">m和n均为非负整数，其中n&lt;=m。最少匹配n次且最多匹配m次。例如，“o{1,3}”将匹配“fooooood”中的前三个o。“o{0,1}”等价于“o?”。请注意在逗号和两个数之间不能有空格。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">?</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									当该字符紧跟在任何一个其他限制符（*,+,?，{n}，{n,}，{n,m}）后面时，匹配模式是非贪婪的。非贪婪模式尽可能少的匹配所搜索的字符串，而默认的贪婪模式则尽可能多的匹配所搜索的字符串。例如，对于字符串“oooo”，“o+?”将匹配单个“o”，而“o+”将匹配所有“o”。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">.点</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">匹配除“\r\n”之外的任何单个字符。要匹配包括“\r\n”在内的任何字符，请使用像“[\s\S]”的模式。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">(pattern)</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									匹配pattern并获取这一匹配。所获取的匹配可以从产生的Matches集合得到，在VBScript中使用SubMatches集合，在JScript中则使用$0…$9属性。要匹配圆括号字符，请使用“\(”或“\)”。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">(?:pattern)</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									非获取匹配，匹配pattern但不获取匹配结果，不进行存储供以后使用。这在使用或字符“(|)”来组合一个模式的各个部分是很有用。例如“industr(?:y|ies)”就是一个比“industry|industries”更简略的表达式。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">(?=pattern)</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									非获取匹配，正向肯定预查，在任何匹配pattern的字符串开始处匹配查找字符串，该匹配不需要获取供以后使用。例如，“Windows(?=95|98|NT|2000)”能匹配“Windows2000”中的“Windows”，但不能匹配“Windows3.1”中的“Windows”。预查不消耗字符，也就是说，在一个匹配发生后，在最后一次匹配之后立即开始下一次匹配的搜索，而不是从包含预查的字符之后开始。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">(?!pattern)</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									非获取匹配，正向否定预查，在任何不匹配pattern的字符串开始处匹配查找字符串，该匹配不需要获取供以后使用。例如“Windows(?!95|98|NT|2000)”能匹配“Windows3.1”中的“Windows”，但不能匹配“Windows2000”中的“Windows”。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">(?&lt;=pattern)</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">非获取匹配，反向肯定预查，与正向肯定预查类似，只是方向相反。例如，“(?&lt;=95|98|NT|2000)Windows”能匹配“2000Windows”中的“Windows”，但不能匹配“3.1Windows”中的“Windows”。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">(?&lt;!pattern)</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">非获取匹配，反向否定预查，与正向否定预查类似，只是方向相反。例如“(?&lt;!95|98|NT|2000)Windows”能匹配“3.1Windows”中的“Windows”，但不能匹配“2000Windows”中的“Windows”。这个地方不正确，有问题</div>
								<div class="para" label-module="para">此处用或任意一项都不能超过2位，如“(?&lt;!95|98|NT|20)Windows正确，“(?&lt;!95|980|NT|20)Windows
									报错，若是单独使用则无限制，如(?&lt;!2000)Windows 正确匹配</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">x|y</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									匹配x或y。例如，“z|food”能匹配“z”或“food”(此处请谨慎)。“(z|f)ood”则匹配“zood”或“food”。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">[xyz]</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">字符集合。匹配所包含的任意一个字符。例如，“[abc]”可以匹配“plain”中的“a”。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">[^xyz]</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">负值字符集合。匹配未包含的任意字符。例如，“[^abc]”可以匹配“plain”中的“plin”。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">[a-z]</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">字符范围。匹配指定范围内的任意字符。例如，“[a-z]”可以匹配“a”到“z”范围内的任意小写字母字符。</div>
								<div class="para" label-module="para">注意:只有连字符在字符组内部时,并且出现在两个字符之间时,才能表示字符的范围;
									如果出字符组的开头,则只能表示连字符本身.</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">[^a-z]</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									负值字符范围。匹配任何不在指定范围内的任意字符。例如，“[^a-z]”可以匹配任何不在“a”到“z”范围内的任意字符。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">\b</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									匹配一个单词边界，也就是指单词和空格间的位置（即正则表达式的“匹配”有两种概念，一种是匹配字符，一种是匹配位置，这里的\b就是匹配位置的）。例如，“er\b”可以匹配“never”中的“er”，但不能匹配“verb”中的“er”。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">\B</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">匹配非单词边界。“er\B”能匹配“verb”中的“er”，但不能匹配“never”中的“er”。</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">\cx</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">
									匹配由x指明的控制字符。例如，\cM匹配一个Control-M或回车符。x的值必须为A-Z或a-z之一。否则，将c视为一个原义的“c”字符。
								</div>
							</td>
						</tr>
						<tr>
							<td width="75" align="left" valign="center">
								<div class="para" label-module="para">\d</div>
							</td>
							<td width="658" align="left" valign="center">
								<div class="para" label-module="para">匹配一个数字字符。等价于[0-9]。grep
									要加上-P，perl正则支持</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\D</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配一个非数字字符。等价于[^0-9]。grep要加上-Pperl正则支持</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\f</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配一个换页符。等价于\x0c和\cL。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\n</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配一个换行符。等价于\x0a和\cJ。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\r</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配一个回车符。等价于\x0d和\cM。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\s</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配任何不可见字符，包括空格、制表符、换页符等等。等价于[
									\f\n\r\t\v]。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\S</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配任何可见字符。等价于[^
									\f\n\r\t\v]。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\t</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配一个制表符。等价于\x09和\cI。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\v</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配一个垂直制表符。等价于\x0b和\cK。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\w</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">
									匹配包括下划线的任何单词字符。类似但不等价于“[A-Za-z0-9_]”，这里的"单词"字符使用Unicode字符集。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\W</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配任何非单词字符。等价于“[^A-Za-z0-9_]”。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\xn</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">
									匹配n，其中n为十六进制转义值。十六进制转义值必须为确定的两个数字长。例如，“\x41”匹配“A”。“\x041”则等价于“\x04&amp;1”。正则表达式中可以使用ASCII编码。
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\num</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配num，其中num是一个正整数。对所获取的匹配的引用。例如，“(.)\1”匹配两个连续的相同字符。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\n</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">
									标识一个八进制转义值或一个向后引用。如果\n之前至少n个获取的子表达式，则n为向后引用。否则，如果n为八进制数字（0-7），则n为一个八进制转义值。
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\nm</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">
									标识一个八进制转义值或一个向后引用。如果\nm之前至少有nm个获得子表达式，则nm为向后引用。如果\nm之前至少有n个获取，则n为一个后跟文字m的向后引用。如果前面的条件都不满足，若n和m均为八进制数字（0-7），则\nm将匹配八进制转义值nm。
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\nml</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">如果n为八进制数字（0-7），且m和l均为八进制数字（0-7），则匹配八进制转义值nml。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\un</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">匹配n，其中n是一个用四个十六进制数字表示的Unicode字符。例如，\u00A9匹配版权符号（&amp;copy;）。</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">
								<div class="para" label-module="para">\p{P}</div>
							</td>
							<td align="left" valign="center">
								<div class="para" label-module="para">小写 p 是 property
									的意思，表示 Unicode 属性，用于 Unicode 正表达式的前缀。中括号内的“P”表示Unicode
									字符集七个字符属性之一：标点字符。</div>
								<div class="para" label-module="para">其他六个属性：</div>
								<div class="para" label-module="para">L：字母；</div>
								<div class="para" label-module="para">M：标记符号（一般不会单独出现）；</div>
								<div class="para" label-module="para">Z：分隔符（比如空格、换行等）；</div>
								<div class="para" label-module="para">S：符号（比如数学符号、货币符号等）；</div>
								<div class="para" label-module="para">N：数字（比如阿拉伯数字、罗马数字等）；</div>
								<div class="para" label-module="para">C：其他字符。</div>
								<div class="para" label-module="para">
									<i>*注：此语法部分语言不支持，例：javascript。</i>
								</div>
							</td>
						</tr>
						<tr>
							<td align="left" valign="center">&lt; &gt;</td>
							<td align="left" valign="center">匹配词（word）的开始（&lt;）和结束（&gt;）。例如正则表达式&lt;the&gt;能够匹配字符串"for
								the
								wise"中的"the"，但是不能匹配字符串"otherwise"中的"the"。注意：这个元字符不是所有的软件都支持的。</td>
						</tr>
						<tr>
							<td align="left" valign="center">( )</td>
							<td align="left" valign="center">将( 和 )
								之间的表达式定义为“组”（group），并且将匹配这个表达式的字符保存到一个临时区域（一个正则表达式中最多可以保存9个），它们可以用
								\1 到\9 的符号来引用。</td>
						</tr>
						<tr>
							<td align="left" valign="center">|</td>
							<td align="left" valign="center">将两个匹配条件进行逻辑“或”（Or）运算。例如正则表达式(him|her)
								匹配"it belongs to him"和"it belongs to her"，但是不能匹配"it belongs to
								them."。注意：这个元字符不是所有的软件都支持的。</td>
						</tr>
						<tr>
							<td align="left" valign="center">+</td>
							<td align="left" valign="center">匹配1或多个正好在它之前的那个字符。例如正则表达式9+匹配9、99、999等。注意：这个元字符不是所有的软件都支持的。</td>
						</tr>
						<tr>
							<td align="left" valign="center">?</td>
							<td align="left" valign="center">匹配0或1个正好在它之前的那个字符。注意：这个元字符不是所有的软件都支持的。</td>
						</tr>
						<tr>
							<td align="left" valign="center">{i} {i,j}</td>
							<td align="left" valign="center">匹配指定数目的字符，这些字符是在它之前的表达式定义的。例如正则表达式A[0-9]{3}
								能够匹配字符"A"后面跟着正好3个数字字符的串，例如A123、A348等，但是不匹配A1234。而正则表达式[0-9]{4,6}
								匹配连续的任意4个、5个或者6个数字</td>
						</tr>
					</tbody>
				</table>
			</div>


		</div>
	</div>
</body>