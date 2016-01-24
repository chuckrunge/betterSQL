# betterSQL
A basic desktop swing app for executing SQL.

//Copyright (C) 2016  Chuck Runge
//Lombard, IL.
//CGRunge001@GMail.com

//This program is free software; you can redistribute it and/or
//modify it under the terms of the GNU General Public License
//as published by the Free Software Foundation; either version 2
//of the License, or (at your option) any later version.

//This program is distributed in the hope that it will be useful,
//but WITHOUT ANY WARRANTY; without even the implied warranty of
//MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
//GNU General Public License for more details.

//You should have received a copy of the GNU General Public License
//along with this program; if not, write to the Free Software
//Foundation, Inc., 51 Franklin Street, Fifth Floor, Boston, MA  02110-1301, USA.

betterSQL will connect to a JDBC database, show you the public tables, and allow you to query or update the table with SQL.  It's intended to be minimalist, giving you just what you need and no more.

I've worked places that did not provide the software I needed to do my job, and I came up with something to meet the need.  The original version was a spreadsheet macro I called LoserBoy SQL, so this version is better (hence, "betterSQL").

Each of the functions are executed by pressing one of five buttons.
1. Connection - This brings up a dialog for connection properties including driver name, connection string, user id, and password.
2. Info - list the public tables available for querying.
3. Execute - When you enter SQL in the edit box, hit Execute to run it gainst the database.
4. Reset - Clear the SQL edit box.  (The connection stays as it is.) 
5. Cancel - Shut down the application. 

The only thing that's not in the jar file is your database driver.  The simple solution is to drop your database driver in the same folder as the jar file.

The code was originally written in an early version of java, but recently updated to Java 8. 