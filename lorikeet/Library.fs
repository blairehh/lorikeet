namespace Lorikeet

module Core =
    [<AbstractClass>]
    type Tract() =
       abstract member invoke: Cell<'A> -> 'A
       static member (<!) (tract: Tract, cell: Cell<'A>): 'A = tract.invoke(cell)
    and Cell<'A> =
        Edict of Edict<'A>
    and Edict<'A> =
        Fun of (Tract -> 'A)