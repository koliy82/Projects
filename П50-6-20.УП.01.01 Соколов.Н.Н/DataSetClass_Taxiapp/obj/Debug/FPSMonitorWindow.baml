   M S B A M L   `   `   `  ????O  KDataSetClass_Taxiapp, Version=1.0.0.0, Culture=neutral, PublicKeyToken=null;"clr-namespace:DataSetClass_TaxiappDataSetClass_Taxiapp  5   	   U QLiveCharts.Wpf, Version=0.9.7.0, Culture=neutral, PublicKeyToken=0bc1f845d1ebb8dfG4clr-namespace:LiveCharts.Wpf;assembly=LiveCharts.WpfLiveCharts.Wpf 5   	   +    %DataSetClass_Taxiapp.FPSMonitorWindow   5   	   W SPresentationCore, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35R NWindowsBase, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35\ XPresentationFramework, Version=4.0.0.0, Culture=neutral, PublicKeyToken=31bf3856ad364e35D 9http://schemas.microsoft.com/winfx/2006/xaml/presentation    5   	   R NSystem.Xaml, Version=4.0.0.0, Culture=neutral, PublicKeyToken=b77a5c561934e089:x,http://schemas.microsoft.com/winfx/2006/xaml     5   	   @d2http://schemas.microsoft.com/expression/blend/2008     5   	   Jmc;http://schemas.openxmlformats.org/markup-compatibility/2006     5   	   4local"clr-namespace:DataSetClass_Taxiapp     5   	   M IModernWpf.Controls, Version=0.9.4.0, Culture=neutral, PublicKeyToken=nullD @ModernWpf, Version=0.9.4.0, Culture=neutral, PublicKeyToken=null,ui!http://schemas.modernwpf.com/2019   5   	   @lvc4clr-namespace:LiveCharts.Wpf;assembly=LiveCharts.Wpf   5   	   0  *ModernWpf.Controls.Primitives.WindowHelper    UseModernWindowStyle  .  ? Title$ FPSMonitorWindow??5
   	   $	??780??5   	   $	??380??6   .??5      ?? .??5   
   )  #ModernWpf.Controls.SimpleStackPanel  .+?5      ??  ?? TextAlignment$ Center=?6   $??ПРОЦЕССОР??6/   $??5,10,0,0q?6@   6T   ?? 5      -   5         ??Name$ CPUCOUNT_tb??5      $??5,0,5,0q?6-   6@   ?? 5      -   5        $ 
CPUSAGE_tb??5      $??5,0,5,0q?6,   6?   #  LiveCharts.Wpf.CartesianChart  5      $	??160??6!   &   LiveCharts.Wpf.Charts.Base.Chart   Series 5        LiveCharts.Wpf.LineSeries    LiveCharts.Wpf.Series   Stroke
 ?  ??   Fill
 ????    Values 6%   ?? *	CPULine+5         AxisX 5        LiveCharts.Wpf.Axis  	   MinValue$	 1Q?6   
   MaxValue$
 5Q?6,      
Foreground
 ?  ??   	Separator 5        LiveCharts.Wpf.Separator  5         Stroke 5   "   ?? 5   &    ?? Color$ 
Aquamarine??66   5      5!         AxisY 5$        $	 0Q?6   $	
 100Q?6,   
 ?  ?? 5%        5&       5'   "   ?? 5(   &   $ 
LightGreen??66   5*      5,      5.      ?? 5/      $ Center=?6   $??ОЗУ (RAM)??6/   $??5,10,0,0q?6@   6T   ?? 50      -   5        $ RAM_tb??50      $??5,0,5,0q?6(   6<     51      $	??160??6!    53        
 ?  ??
 ????  6%   ?? *	RAMLine+55       58        $	 1Q?6   $
 5Q?6,   
 ?  ?? 59        5:       5;   "   ?? 5<   &   $ 
Aquamarine??66   5>      5@       5C        $	 0Q?6   
 ?  ??
 6,   ?? *RAMMax+ 5D        5E       5F   "   ?? 5G   &   $ 
LightGreen??66   5I      5K      5M      ?? 5N      $ Center=?6   $??ДИСКИ (ROM)??6/   $??5,10,0,0q?6B   6V   ?? 5O      -   5        $ ROM_tb??5O      $??5,0,5,0q?6(   6;     5P      $	??160??6!    5R        
 ?  ??
 ????  6%   ?? *	ROMLine+5T       5W        
 ?  ?? 5X        5Y       5Z   "   ?? 5[   &   $ 
Aquamarine??66   5]      5_       5b        
 ?  ?? 5c        5d       5e   "   ?? 5f   &   $ 
LightGreen??66   5h      5j      5l      ?? 5n      $ Center=?6   $??Сеть??6/   $??5,10,5,0q?6;   6O     5o      $	??160??6!    5q        
 ?  ??
 ????  6%   ?? *	SpeedLine+5s       5v        $	 1Q?6   $
 5Q?6,   
 ?  ?? 5w        5x       5y   "   ?? 5z   &   $ 
Aquamarine??66   5|      5~       5?        $	 0Q?6   $

 1000Q?6,   
 ?  ?? 5?        5?       5?   "   ?? 5?   &   $ 
LightGreen??66   5?      5?      5?      ?? 5?      -   5        $ 
Network_tb??5?      $??5,0,5,0q?6,   6?   5?      5?      5?      