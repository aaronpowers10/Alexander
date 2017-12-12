# Alexander

Alexander is a tool for weather files used in building simulation.  The core package contains tools for a generic weather file.  Specific weather format packages must implement this core.  Currently two weather formats have been implemented: DOE2 .bin format, and NOAA format.  Alexander can read and write to both of these formats.  After reading a weather file, Alexander provides data structures which hold the contents of the file in the generic format.  These data structures can be used by external simulation tools to retrieve and use or manipulate the weather data.  These data structures can then be saved back to any weather format that has been implemented.

## Dependencies

None

## License

Rufus is licensed under the Apache 2.0 license.  See [LICENSE](LICENSE.md) for details.

## Authors

Initial Author: Aaron Powers <caaronpowers@gmail.com>

