## Leassets Server

This server is part of the leases and assets management platform called leassets. It is a draft implementation that illustrates how the two concerns for a typical organization can be automated for purposes of record keeping, reporting and analysis. 

This server in particular handles responses for a client and is designed to be linked into any system of a microservice stack through eureka.

### Notes on Fixed Assets

This is not the first attempt to create a system for fixed assets management; this iteration however is more promising than previous projects especially because of the benefit of experience and also exposure to new tools to which the developer had not the access or intuition thereof. 
The implementation is very specific to the needs of the developer but it's hoped that who finds this code will find it useful for their projects or organization or that they may find the structure of the code, flexible enough to be framed and tuned for their own purposes. 

As previously mentioned there have been attempts whose ultimate failures inform in one way or the other the design of the poject, there being no other easily and freely available material on design of fixed assets systems or how best to structure assets depreciation on large scale. This time around the developer feels (gut feeling) that the best approach is to avoid creating a big asset entity containing most properties of the fixed asset item; instead multiple entities are designed each which represents a quality of the physical fixed asset e.g. asset-acquisition, asset's net book value, asset dpreciation. 

The entities are created to have independent existence and their relation to each other is a merely a function of an arbitrily assigned asset-number attribute contained in each of the entities. The fixed asset itself does not exist but if needed an aggregate of the effect of the instances of the three entities can be generated from queries on the database.

The core entities (representing qualities of a fixed asset) are as follows

#### Fixed Asset Acquisition

This contains base details of the asset, its purchase date and purchase cost. The fields are as follows

 - asset-number (unique): Long
 - service-outlet-code: String
 - asset-tag: String
 - asset-description: String
 - purchase-date: LocalDate
 - asset-category: String
 - purchase-price: BigDecimal
 - file-upload-token: String

#### Fixed Asset Net Book Value

This entity contains the base details of the asset, the net book value of the asset and the net book value date. It is a transient instance whose value changes varies for every reporting period

 - asset-number: Long
 - service-outlet-code: String
 - asset-tag: String
 - asset-description: String
 - net-book-value-date: LocalDate
 - asset-category: String
 - net-book-value: BigDecimal
 - depreciation-regime: DepreciationRegime (Enumeration)
 - file-upload-token: String
 - compilation-token: String

#### Fixed Asset Depreciation

This entity contains the base details of the asset, the depreciation date and the depreciation amount on the depreciation date. This entity creates instances which are transient for every reporting period

 - asset-number: Long
 - service-outlet-code: String
 - asset-tag: String
 - asset-description: String
 - depreciation-date: LocalDate
 - asset-category: String
 - depreciation-amount: BigDecimal
 - depreciation-regime: DepreciationRegime (Enumeration)
 - file-upload-token: String
 - compilation-token: String

This design enables us to quickly compute summaries and aggregate values of the three entities at any given point in time. The three can also be aggregated to create a virtual physical asset with all attributes combined.

The developer's understanding concerning initial state of the application is that initial depreciation data will contain the lifetime depreciation of an asset up to the initialization date. This will enable the system to create an up to date net book value instance for the entity as at the date of initialization without running expensive period-by-period depreciation procedures for each asset since its specific purchase date. 
The reason this is important is because one of the depreciation regimes is declining balance basis which calculates the depreciation of an asset based on the net book value of the same at the end of the period prior to the period of depreciation. It's also good to be able to reflect an exact and accurate net book value on the initialization date itself.


### Batch processes

This system makes heavy use of spring batch processes as it is designed to handle large amounts of data. Most such processes each depreciation, upload of large data sets or reconcialition are framed as compilation-request objects. 

The compilation request object that creates depreciation instances for a given reporting period is also the same that creates the net book value instances for the same period. The two are then marked with a compilation token to mark their origin to a known compilation request. It's such that upon the deletion of an instance of such a request that all instances of depreciation and net book values will be deleted from the system restoring the database to its original state before the compilation-request  was created.

A file upload object is also designed to upload batch lists of data using an excel file. Each instance will have a file-upload-token marking each entity  with a hash code similar to the instance containing the file from which its data was originating. Deletion of the file-upload entity will have the same effect of triggering the deletion of instances of the core entities whose file-upload-token matches that of the file-uplaod instance being deleted.

This sequences are carefully considered and weighed with the design of making it easy to upload data from a file or creating huge swaths of data and also giving the user the power to undo mistakes hidden in a huge file upload, by deleting the entire dataset.

### Depreciation

This system is designed to have two methods of depreciation

 - Straight line basis
 - Declining balance basis

Tentatively the depreciation method and the depreciation rate attributes are contained in instances of the asset-category entity. 

In reality the depreciation sequence will create instances from both regimes. Then when requests are made to show the depreciation or net book value at a given date the system refers to user-created rules of the nature of depreciation for each entity and returns the appropriate value. But should the user change the category's type of depreciation, the system will simply start reflecting the alternate set of values and positions; so at the time that the user decides to change the depreciation regime say for instance electronic equipment from straight line to declinining balance the system will not start recalculating, because the instance will already be in existence as at the requested date.

The only values that will not benefit from this flexibility will be the values of depreciation at the time of initialization.

The developer is aware that these are strict contraints on the design of a supposedly flexible enterprise tool, but previous attempts to become even more flexible have resulted in code that grew to mountains of un-maintainable spaghetti.


### Support or Contact

In case of queries try the [email](mailto:mailnjeru@gmail.com)
