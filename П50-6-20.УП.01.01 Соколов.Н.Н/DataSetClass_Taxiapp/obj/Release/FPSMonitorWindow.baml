   M S B A M L   `   `   `  ???? O  KDataSetClass_Taxiapp, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null;"clr-namespace:DataSetClass_TaxiappDataSetClass_Taxiapp  U QLiveCharts.Wpf, Version=0.9.7.0, Culture=neutral, PublicKeyToken=0bc1f845d1ebb8dfG4clr-namespace:LiveCharts.Wpf;assembly=LiveCharts.WpfLiveCharts.Wpf +    %DataSetClass_Taxiapp.FPSMonitorWindow   W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     @d2http://schemas.microsoft.com/expression/blend/2008     Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     4local"clr-namespace:DataSetClass_Taxiapp     M IModernWpf.Controls, Version=0.9.4.0, Culture=neutral, PublicKeyToken=nullD @ModernWpf, Version=0.9.4.0, Culture=neutral, PublicKeyToken=null,ui!http://schemas.modernwpf.com/2019   @lvc4clr-namespace:LiveCharts.Wpf;assembly=LiveCharts.Wpf   0  *ModernWpf.Controls.Primitives.WindowHelper    UseModernWindowStyle  .  ? Title$ FPSMonitorWindow??$	??780??$	??380??.???? .??)  #ModernWpf.Controls.SimpleStackPanel  .+???  ?? TextAlignment$ Center=?$??ПРОЦЕССОР??$??5,10,0,0q??? -    ??Name$ CPUCOUNT_tb??$??5,0,5,0q??? -   $ 
CPUSAGE_tb??$??5,0,5,0q?#  LiveCharts.Wpf.CartesianChart  $	??160??&   LiveCharts.Wpf.Charts.Base.Chart   Series   LiveCharts.Wpf.LineSeries    LiveCharts.Wpf.Series   Stroke
 ?  ??   Fill
 ????    Values ?? *	CPULine+   AxisX   LiveCharts.Wpf.Axis  	   MinValue$	 1Q?
   MaxValue$
 5Q?   
Foreground
 ?  ??   	Separator   LiveCharts.Wpf.Separator     Stroke ??  ?? Color$ 
Aquamarine??   AxisY   $	 0Q?$	
 100Q?
 ?  ??    ?? $ 
LightGreen???? $ Center=?$??ОЗУ (RAM)??$??5,10,0,0q??? -   $ RAM_tb??$??5,0,5,0q?  $	??160??   
 ?  ??
 ????  ?? *	RAMLine+   $	 1Q?$
 5Q?
 ?  ??    ?? $ 
Aquamarine??   $	 0Q?
 ?  ??
 ?? *RAMMax+    ?? $ 
LightGreen???? $ Center=?$??ДИСКИ (ROM)??$??5,10,0,0q??? -   $ ROM_tb??$??5,0,5,0q?  $	??160??   
 ?  ??
 ????  ?? *	ROMLine+   
 ?  ??    ?? $ 
Aquamarine??   
 ?  ??    ?? $ 
LightGreen???? $ Center=?$??Сеть??$??5,10,5,0q?  $	??160??   
 ?  ??
 ????  ?? *	SpeedLine+   $	 1Q?$
 5Q?
 ?  ??    ?? $ 
Aquamarine??   $	 0Q?$

 1000Q?
 ?  ??    ?? $ 
LightGreen???? -   $ 
Network_tb??$??5,0,5,0q?