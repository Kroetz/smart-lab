# Smart Lab

This repository accompanies the Master's thesis "Smart worspaces for collaboration in projects" and contains Smart Lab, a system that provides (semi-)automated contextual alligned assistance functions during meetings. Smart Lab is built upon a service-oriented architecture and orchestrates devices, programs and webservices to create a benefit for the users. Up until now the system's functionality covers 5 use cases:

* Taking minutes during meetings (that includes creating an audio protocol, converting the audio data into a textual transcript and uploading the data to a project base, e.g. GitHub)
* Displaying a website for the duration of a meeting on a display of your choice
* Displaying the content of a file for the duration of a meeting on a display of your choice
* Displaying the agenda of a meeting for its duration on a display of your choice
* Activating devices (e.g. beamers) for the duration of a meeting

All these assiatance functions are controlled by the principle of fire-and-forget. Smart Lab can be integrated into the Google Calendar so that the available functionality can be accessed from there. For this purpose a special configuration language is used that must be placed in the description field of Google Calendar events.

The documentation of Smart Lab is only available in form of the Master's thesis itself and only in german. The appropriate document can be found inside this repository.

The Master's thesis was written by Dennis Schock at the QAware GmbH and the Munich University of Applied Sciences.

A remark about the license: The code of Smart Lab is open source and released under the GNU GPLv3.