CatalogItem class:- must exist before lists, rows, lambdas. (used by other phases)
{needed at class level, as it provides definitions, classes are definitions, Definitions live at class level.}


List<CatalogItem> items = new ArrayList<>(); - class-level field (Shared state lives at class level.){
 UI setup,
 UI rendering, 
 methods need it for accessing data, 
 Data belongs to screen (Data is not owned by a column), 
 represents State  }


 
TableViewer tableViewer = new TableViewer(...) [UI widgets live where the UI is built.];
{ UI objects must be created after the Shell exists, 
  The table must exist before columns can be added
  are tied to the lifecycle of the window,
  building the screen, 
  cannot exist before the UI is initialised, [requires a live UI context] }



createColumn(tableViewer, "Name", ...); [Rules before data.] {
  Columns need a table to attach to, Data must not arrive before rules exist, }



tableViewer.setInput(items); [Data connection comes last. Always.]
{ triggers rendering, Everything must already be configured}

private static void createColumn(...) { ... } [Repeated logic lives in helper methods.]
{ Java allows calling methods defined later, Keeps UI code clean and readable}


// Each code block is placed where its responsibility naturally belongs: definitions at the top, state at class level, UI assembly in setup flow, triggers at the end, and reusable logic in helpers.

/*
Top      → Definitions (what things ARE)
Middle   → UI assembly (how things CONNECT)
Bottom   → Helpers (how things WORK internally)


The order is for humans,
The execution is for the framework,
And Java connects it all safely 💗
*/

  



