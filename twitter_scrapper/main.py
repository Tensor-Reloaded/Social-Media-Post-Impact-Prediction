import datetime

from src.logic import SimpleConsoleOrchestrator, NoneValueConsoleOrchestrator
import datetime as dt

if __name__ == '__main__':
    process = SimpleConsoleOrchestrator(
        export_file="res/2021.10.12.json",
        target_date="2021.10.12",
        q="travel OR travelling OR traveller OR trip OR cruise OR journey OR tourist OR peregrination OR pilgrimage OR voyage OR excursion OR wayfaring OR voyager OR tour OR itinerant OR peregrine OR peregrinate OR wayfare OR sightseer OR holydaymaker OR cruise OR tourism OR photography  OR holiday  OR vacation  OR nature  OR traveladdict  OR travelblogger  OR travelphotography",
        lang="en",
        until=dt.datetime(2021, 10, 13, 0, 0, 0)
    )
    process.main()
