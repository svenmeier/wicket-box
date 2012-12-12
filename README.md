wicket-box
==========

Enhancements for your [Wicket](http://wicket.apache.org) components:

- enhance your `DataTable` with fixed header and resizing of columns
- works on ´TableTree´ too
- specify initial column widths
- resized column widths are stored in cookies
- tested in Firefox, Safari, Chrome, Opera

See our live examples on [http://wicket-box.appspot.com](http://wicket-box.appspot.com) (beware - very slow!).

Themes
------

You have to add a theme (build-in or your own) to your component to enable it for dynamic layouts, e.g.

    add(new BasicTheme());

Dependency
----------

    <dependency>
      <groupId>com.github.svenmeier.wicket-box</groupId>
      <artifactId>wicket-box</artifactId>
      <version>0.6-SNAPSHOT</version>
    </dependency>
