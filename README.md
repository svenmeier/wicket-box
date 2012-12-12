wicket-box
==========

Enhancements for your [Wicket](http://wicket.apache.org) components:

- dynamic behaviors utilizing Wicket's build-in jQuery
- lightweight implementation tailored for Server-side rendered markup
- allow resizing of table columns
- stretch parts of markup
- optional cookie persistence
- synchronized scroll between markup
- tested in Firefox, Safari, Chrome, Opera

See our live examples on [http://wicket-box.appspot.com](http://wicket-box.appspot.com) (beware - very slow!).

Themes
------

You have to add a theme (build-in or your own) to your component to enable it for dynamic layouts, e.g.

    add(new BasicTheme());

Components
----------

- DataBox is a DataTable extension which bundles all behaviors into a highly dynamic table
  with fixed headers and column resizing
- TreeBox is the pendant for TableTree 
- WizardBox is an example on how to use wicket-box behaviors to fix parts of a component's markup to locations not supported by CSS.

Dependency
----------

    <dependency>
      <groupId>com.github.svenmeier.wicket-box</groupId>
      <artifactId>wicket-box</artifactId>
      <version>0.6-SNAPSHOT</version>
    </dependency>

For snapshot releases you have to use the [OSS Sonatype Snapshot repository](https://oss.sonatype.org/content/repositories/snapshots/com/github/svenmeier/wicket-box/):

    <repository>
      <id>sonatype-snapshots</id>
      <url>https://oss.sonatype.org/content/repositories/snapshots</url>
      <snapshots>
        <enabled>true</enabled>
      </snapshots>    	
    </repository>

Releases will soon be available on [Maven central](http://repo1.maven.org/maven2/com/github/svenmeier/wicket-box)
and [OSS Sonatype](https://oss.sonatype.org/content/repositories/releases/com/github/svenmeier/wicket-box).


