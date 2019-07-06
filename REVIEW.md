1) Exception handling has to be generic for ease of use and testing. This has been partially
implemented.
2) Introduce Data Transfer Object for the sake of demarcation of database layer and
transport layer.
3) Lack of pagination may be potentially dangerous, so must be addressed. Navigation section in DTO is nice have.


Addressed:
1) Primary keys flaw. Initial implementation was prone to race condition. Redesigned version
relies on the engine when it come to Pk generation.