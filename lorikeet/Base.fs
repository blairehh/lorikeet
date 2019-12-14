module lorikeet.Base

open lorikeet.Core

type DefaultTract() as this =
    inherit Tract()
    let invokeEdict edict = match edict with
        Fun func -> func this
    override this.invoke(cell: Cell<'a>): 'a = match cell with
        Edict edict -> invokeEdict edict
      