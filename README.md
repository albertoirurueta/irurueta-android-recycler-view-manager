# irurueta-android-recycler-view-manager
Recycler view utility to simplify adapter notifications when collections of data are modified

[![Build Status](https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/actions/workflows/main.yml/badge.svg)](https://github.com/albertoirurueta/irurueta-android-recycler-view-manager/actions)

[![Code Smells](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=code_smells)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)
[![Coverage](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=coverage)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)

[![Duplicated lines](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=duplicated_lines_density)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)
[![Lines of code](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=ncloc)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)

[![Maintainability](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=sqale_rating)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)
[![Quality gate](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=alert_status)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)
[![Reliability](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=reliability_rating)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)

[![Security](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=security_rating)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)
[![Technical debt](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=sqale_index)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)
[![Vulnerabilities](https://sonarcloud.io/api/project_badges/measure?project=albertoirurueta_irurueta-android-recycler-view-manager&metric=vulnerabilities)](https://sonarcloud.io/dashboard?id=albertoirurueta_irurueta-android-recycler-view-manager)

[![Maven Central](https://maven-badges.herokuapp.com/maven-central/com.irurueta/irurueta-android-recycler-view-manager/badge.svg)](https://search.maven.org/artifact/com.irurueta/irurueta-android-recycler-view-manager/1.0.0/aar)

[API Documentation](http://albertoirurueta.github.io/irurueta-android-recycler-view-manager)

## Overview

This library contains a utility class to simplify adapter notifications when collections of data
are modified.

The class can process a pair of collections of data and generate the necessary adapter notifications
when items are added, removed, moved or changed.

![Demo](docs/recycler-view-manager.gif)

## Usage

Add the following dependency to your project:

```
implementation 'com.irurueta:irurueta-android-recycler-view-manager:1.0.4'
```

You can refer to the sample app for a complete example of how to use this library.

In summary, the following steps are required:
- Identify in your data model what identifies each item, and when it is considered that the content
  of an item has changed.
  For instance, if the following data model is used:
  ```
    data class Item(val id: Long, val name: String)
  ```
  The id field is used to identify each item, and the name field is used to determine if the content
  of an item has changed.
- Create a RecyclerView.Adapter and RecyclerView.ViewHolder as usual to inflate and update the
  contents of the views managed by the RecyclerView.
- Create a RecyclerViewManager that accepts the adapter and comparators to determine whether items
  in the data model are the same or their contents have changed.
  For instance:
  ```
    private val manager = RecyclerViewManager<ItemViewHolder, Item>(
        adapter,
        { item1, item2 -> item1.id == item2.id },
        { item1, item2 -> item1.name == item2.name }
    )
  ```
- Whenever your items change make sure that:
    - You recycler view adapter receives the new items (the getItemCount must return the number of
      new items).
    - You call the process method of the RecyclerViewManager with the new items and the old items.
      For instance:
      ```
        manager.process(newItems, oldItems)
      ```
      This will generate all the required adapter notifications to reflect the changes in the
      RecyclerView.