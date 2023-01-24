using DiscordRPC;
using DiscordRPC.Logging;
using Button = DiscordRPC.Button;
using System;
using System.Threading;
using System.IO;
using HtmlAgilityPack;
using System.Collections.Generic;
using System.Diagnostics;

namespace DircordRPC_sharp
{
	
	class Program
    {
		public static DiscordRpcClient client;
		public static string discordState = "...";
		static void Main(string[] args)
		{
			Thread thread = new Thread(() =>
			{
				bool exit = true;
				try
                {
					windowSettings();
					startRichPresence();
                    //Process process = new Process
                    //{
                    //    StartInfo = new ProcessStartInfo(@"Game-Launch.exe")
                    //};
                    //process.Start();

					//File.Create("RPCState").Close();
					//File.WriteAllText("RPCState", "Загружаемся...");

					while (exit)
					{
						Thread.Sleep(10000);
						//discordState = File.ReadAllText("RPCState");
						client.UpdateState($"1$ = {parseCurrentCourse()}₽");
						//if (process.HasExited == true) Environment.Exit(0);
					}
				}
				catch (Exception ex)
                {
					Console.WriteLine(ex.Message);
					System.Windows.Forms.MessageBox.Show("Всё, критическая", "Critical Error");
                }
			});
			thread.Start();

			string parseCurrentCourse()
            {
                try
                {
					var url = "https://ru.investing.com/currencies/usd-rub";
					var web = new HtmlWeb();
					HtmlDocument doc = web.Load(url);
					foreach (var a in doc.DocumentNode.SelectNodes("//span[@data-test = 'instrument-price-last']"))
					{
						Console.WriteLine($"1$ = {a.InnerText}₽");
						return a.InnerText;
					}
					return "0";
                }catch (Exception ex)
                {
					Console.WriteLine(ex.Message);
                }
				return null;
			}

			void windowSettings()
			{
				Console.Title = "ConsoleMrEblan";
			}

			void startRichPresence()
			{
				string appID = "840348888807243818";
				client = new DiscordRpcClient(appID);
				client.Logger = new ConsoleLogger() { Level = LogLevel.Warning };
				client.OnReady += (sender, e) =>
				{
					Console.WriteLine($"Discord RPC connected to {e.User.Username}#{e.User.Discriminator}");
				};
				client.OnPresenceUpdate += (sender, e) =>
				{
					Console.WriteLine("Discord RPC updated.");
				};
				client.Initialize();
				client.SetPresence(new RichPresence()
				{
					Details = "Курс рубля",
					State = "Загружаемся...",
					Assets = new Assets()
					{
						LargeImageKey = "mzlff-phonarik",
						LargeImageText = "text",
						SmallImageKey = "mreblan512-512",
						SmallImageText = "Smile"
					},
					Buttons = new Button[] {
						new Button()
						{
							Label = "Скачать",
							Url = "https://koliy82.itch.io/mzlff"
						},
						new Button()
						{
							Label = "Twich Мазяки",
							Url = "https://www.twitch.tv/mazellovvv"
						}
					}
				});
			}
		}
	}
}
