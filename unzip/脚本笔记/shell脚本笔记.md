# 公司shell脚本笔记

<img src="C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804092135711.png" alt="image-20210804092135711" style="zoom:67%;" />

parameter：表示当前时间，后面是时间格式

![image-20210804092231950](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804092231950.png)

* -f：校验常规文件是否存在，如果不存在则告知

* 错误退出

![image-20210804093054483](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804093054483.png)

设置脚本环境：传入各种参数

* org_id：id
* GROUP：组别

* batch_date：批处理时间

![image-20210804093114228](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804093114228.png)

如果(GROUP等于“input_xbCashStopList”**或者**等于“input_ecifSyn”)为真时：

批处理时间设置为一天后。

* 最后一次批处理时间为1天前
* 下一次批处理时间为1天后

![image-20210804094020895](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804094020895.png)

* 设置批处理时间为，截取2位后的时间

* 设置业务时间

* 传入列表文件

* 设置列表文件名
* 传入md5文件值
* 设置传入需求时间
* 传入批处理号
* 传入批处理编号

![image-20210804094632647](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804094632647.png)

* 导入fps脚本
* 函数运算结果不等于0，则告知错误退出
* 导入fps_default脚本

![image-20210804095649803](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804095649803.png)

log函数：

* 输出时间

![image-20210804095858268](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804095858268.png)

如果组别的0-16位为"output_PBOC10003",则：

* 设置文件名
* 设置md文件为：

![image-20210804100651402](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804100651402.png)

如果默认睡眠时间为空，则设置默认睡眠时间为10

![image-20210804100733046](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804100733046.png)

导入一堆脚本文件

![image-20210804100830441](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804100830441.png)

设置fps的类别为GROUP的变量“_”前的所有变量名

![image-20210804101101321](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804101101321.png) 

* 设置日志文件路径

* 设置日志文件名
* 设置结果文件路径
* 结果文件名

![image-20210804101623119](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804101623119.png)

* 检查LOG_FILE_PATH是否是目录，不是则创建
* 检查RESULT_FILE_PATH是否为目录，不是则创建
* 检查LOG_FILE是否为普通文件，不是则创建
* 检查RESULT_FILE是否为普通文件，不是则创建

![image-20210804101932694](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804101932694.png)

* 输出1到日志文件中，输出2到日志文件中
* 如果MONITORING_PATH目录不存在则创建
* 如果MONITORING_FILE目录不存在则创建

![image-20210804104131863](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804104131863.png)

* 把元素追加到日志文件中
* 如果编号不等于12，则把write_result设置为null ？？？
* 如果ftp的类型不是input并且类型也不是output？则？？？

![image-20210804104655036](C:\Users\zhusp\AppData\Roaming\Typora\typora-user-images\image-20210804104655036.png)

* 输出文件id列表，将其重复项删除后复制给FILE_IDS

![image-20210804110343264](shell脚本笔记.assets/image-20210804110343264.png)

* （如果默认的输入和输出的下一文件不为空并且前9位不为空）为假，则设置这个变量为空。

* （如果默认的输入和输出的下一文件不为空并且前9位不为空）为真，则设置输出文件为输入文件变量/设置输入文件为输出文件的名。

* （如果默认的输入和输出的下一文件为空并且前9位为空）为真，则设置d_NextFile为空

* （如果默认的输入和输出的下一文件为空并且前9位不为空）为真，则设置d_NextFile变量为y

  ![image-20210804111501454](shell脚本笔记.assets/image-20210804111501454.png)

* 如果fps的类型为input为真，则设置temp为默认的下个文件的input地址替换为截取字符串

* 如果fps的类型为output为真，则设置temp为默认的下个文件的output地址替换为截取字符串

* 默认下一文件休眠时间为temp的数组一号元素

* 默认下一文件号为temp的数组二号元素

![image-20210804112554528](shell脚本笔记.assets/image-20210804112554528.png)

* 初始化文件id为空数组
* 循环文件id，读取文件LIST_FILE，搜索组名，将其查到结果进行判断，如果文件id等于传入参数，则输出出来。
* 设置文件ids为n_FILE_IDS

接下去是一个很长的无限循环方法，退出用的break，以下是对代码的解析。

![image-20210804140256034](shell脚本笔记.assets/image-20210804140256034.png)

![image-20210804141426379](shell脚本笔记.assets/image-20210804141426379.png)

![image-20210804143402097](shell脚本笔记.assets/image-20210804143402097.png)

![image-20210804143726874](shell脚本笔记.assets/image-20210804143726874.png)

![image-20210804143950456](shell脚本笔记.assets/image-20210804143950456.png)

![image-20210804145227267](shell脚本笔记.assets/image-20210804145227267.png)

这里不是为空，是设置为名字+类型。

![image-20210804150027441](shell脚本笔记.assets/image-20210804150027441.png)

这里是如果文件标记为Y，则判断标记是否为wait或者failed，是则退出。

![image-20210804145935841](shell脚本笔记.assets/image-20210804145935841.png)

![image-20210804151348559](shell脚本笔记.assets/image-20210804151348559.png)

![image-20210804152558554](shell脚本笔记.assets/image-20210804152558554.png)

