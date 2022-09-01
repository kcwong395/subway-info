# subway-info
This project aims to tell information regarding subway schedule.

## Requirements:
1. Some method should exist that can tell a caller when the next train will arrive at a specific station, given a time and direction (north, south, east, west).
2. A simple schedule should be implemented, that can be looked up by station, to determine the times at which trains will arrive at that station.
3. As noted above, the data model should account for direction (north, south, east, west) and handle stations that have intersecting lines.

## Special Cases:
1. Union Station has 2 Northbound Stations, 1 to Vaughan Metropolitan Centre, 1 to Finch. With further clarification, NB is terminating at Vaughan while Finch is now the SB.

## Input Data Format
Input data will provide all arrival time to a particular station at each corresponding direction
txt file consists of multiple lines: Station Name,Arrival Time,Direction 
ie, Vaughan Metropolitan Centre,2022-08-30 07:00,South

## Assumption
1. trains only start from terminal stations
2. the train will only follow one direction (ie, a train going south from Finch will only go south and will not go backwards)
3. a train will only traverse in a line (a train in line 1 will always stay in line 1 and will not traverse to another line, except for the common stations shared between lines)
4. trains will not arrive any station at the same time, given they are in the same direction