wicket-box
==========

Enhancements for your [Wicket](http://wicket.apache.org) components:

- dynamic behaviors utilizing Wicket's build-in jQuery
- lightweight implementation tailored for Server-side rendered markup
- resize component markup
- allow resizing of table columns
- synchronized scroll between markup
- stretch markup to locations not supported by CSS
- optional cookie or server-side persistence
- tested in Firefox, Safari, Chrome, Opera and IE

See our live examples on [http://wicket-box.appspot.com](http://wicket-box.appspot.com).

Themes
------

You have to add a theme (build-in or your own) to your component to enable it for dynamic layouts, e.g.

    add(new BasicTheme());

Components
----------

- DataBox is a DataTable extension which bundles all behaviors into a highly dynamic table
  with fixed headers, column resizing and its body stretching to the available size
- TreeBox is the pendant for TableTree 
- SplitBox splits the size of its markup between a resizable main and a remainder component
- WizardBox streches its content so its control buttons are pinned to the bottom of its markup area.

Dependency
----------

Releases are available on [Maven central](http://repo1.maven.org/maven2/com/github/svenmeier/wicket-box)
and [OSS Sonatype](https://oss.sonatype.org/content/repositories/releases/com/github/svenmeier/wicket-box).

    <dependency>
      <groupId>com.github.svenmeier.wicket-box</groupId>
      <artifactId>wicket-box</artifactId>
      <version>0.6.0</version>
    </dependency>

For snapshot releases you have to use the [OSS Sonatype Snapshot repository](https://oss.sonatype.org/content/repositories/snapshots/com/github/svenmeier/wicket-box/):

    <dependency>
      <groupId>com.github.svenmeier.wicket-box</groupId>
      <artifactId>wicket-box</artifactId>
      <version>0.6.1-SNAPSHOT</version>
    </dependency>

    <repository>
      <id>sonatype-snapshots</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>    	
    </repository>

