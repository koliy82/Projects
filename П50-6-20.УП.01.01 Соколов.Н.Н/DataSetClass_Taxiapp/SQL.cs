using System;
using System.Collections.Generic;
using System.Data;
using System.Data.SqlClient;
using System.Linq;
using System.Text;
using System.Threading.Tasks;
using System.Windows;

namespace DataSetClass_Taxiapp
{
    public class SQL
    {
        public SqlConnection sql;
        SqlCommand command = new SqlCommand();
        public DataTable table = new DataTable();
        public SqlDependency dependency = new SqlDependency();
        public SQL(string Server, string BD)
        {
            sql = new SqlConnection($@"Data Source = .\{Server}; Initial Catalog = {BD}; Integrated Security = true;");
        }
        public SQL(string Server, string BD, string masterLog,string masterPass)
        {
            sql = new SqlConnection($@"Data Source = .\{Server}; Initial Catalog = {BD}; User ID = {masterLog}; Password = {masterPass}");
        }
        public DataTable selectTable(string Request)
        {
            DataTable dataTable = new DataTable();
            try
            {
                command.Connection = sql;
                command.CommandText = Request;
                command.Notification = null;
                sql.Open();
                dataTable.Load(command.ExecuteReader());
            }
            catch(Exception a)
            {
                MainWindow.LogExcep(a);
            }
            finally
            {
                sql.Close();
            }
            return dataTable;
        }
        public void action(string Request)
        {
                command.Connection = sql;
                command.CommandText = Request;
                command.Notification = null;
                try
                {
                    sql.Open();
                    command.ExecuteNonQuery();
                }
                catch (Exception e)
                {
                    MainWindow.exMessage(e);
                }
                finally
                {
                    sql.Close();
                }
            }
    }
}
