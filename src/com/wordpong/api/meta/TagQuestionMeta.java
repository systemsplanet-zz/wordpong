package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-05-10 21:59:38")
/** */
public final class TagQuestionMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.TagQuestion> {

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.TagQuestion, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.TagQuestion, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CollectionUnindexedAttributeMeta<com.wordpong.api.model.TagQuestion, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key> keys = new org.slim3.datastore.CollectionUnindexedAttributeMeta<com.wordpong.api.model.TagQuestion, java.util.List<com.google.appengine.api.datastore.Key>, com.google.appengine.api.datastore.Key>(this, "keys", "keys", java.util.List.class);

    /** */
    public final org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.TagQuestion> tag = new org.slim3.datastore.StringAttributeMeta<com.wordpong.api.model.TagQuestion>(this, "tag", "tag");

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.TagQuestion, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.TagQuestion, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final TagQuestionMeta slim3_singleton = new TagQuestionMeta();

    /**
     * @return the singleton
     */
    public static TagQuestionMeta get() {
       return slim3_singleton;
    }

    /** */
    public TagQuestionMeta() {
        super("TagQuestion", com.wordpong.api.model.TagQuestion.class);
    }

    @Override
    public com.wordpong.api.model.TagQuestion entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.TagQuestion model = new com.wordpong.api.model.TagQuestion();
        model.setKey(entity.getKey());
        model.setKeys(toList(com.google.appengine.api.datastore.Key.class, entity.getProperty("keys")));
        model.setTag((java.lang.String) entity.getProperty("tag"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.TagQuestion m = (com.wordpong.api.model.TagQuestion) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setUnindexedProperty("keys", m.getKeys());
        entity.setProperty("tag", m.getTag());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.TagQuestion m = (com.wordpong.api.model.TagQuestion) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.TagQuestion m = (com.wordpong.api.model.TagQuestion) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.TagQuestion m = (com.wordpong.api.model.TagQuestion) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.TagQuestion m = (com.wordpong.api.model.TagQuestion) model;
        long version = m.getVersion() != null ? m.getVersion().longValue() : 0L;
        m.setVersion(Long.valueOf(version + 1L));
    }

    @Override
    protected void prePut(Object model) {
    }

    @Override
    public String getSchemaVersionName() {
        return "slim3.schemaVersion";
    }

    @Override
    public String getClassHierarchyListName() {
        return "slim3.classHierarchyList";
    }

    @Override
    protected boolean isCipherProperty(String propertyName) {
        return false;
    }

    @Override
    protected void modelToJson(org.slim3.datastore.json.JsonWriter writer, java.lang.Object model, int maxDepth, int currentDepth) {
        com.wordpong.api.model.TagQuestion m = (com.wordpong.api.model.TagQuestion) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getKeys() != null){
            writer.setNextPropertyName("keys");
            encoder = new org.slim3.datastore.json.Default();
            writer.beginArray();
            for(com.google.appengine.api.datastore.Key v : m.getKeys()){
                encoder.encode(writer, v);
            }
            writer.endArray();
        }
        if(m.getTag() != null){
            writer.setNextPropertyName("tag");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getTag());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.TagQuestion jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.TagQuestion m = new com.wordpong.api.model.TagQuestion();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("keys");
        decoder = new org.slim3.datastore.json.Default();
        {
            java.util.ArrayList<com.google.appengine.api.datastore.Key> elements = new java.util.ArrayList<com.google.appengine.api.datastore.Key>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("keys");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    com.google.appengine.api.datastore.Key v = decoder.decode(reader, (com.google.appengine.api.datastore.Key)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setKeys(elements);
            }
        }
        reader = rootReader.newObjectReader("tag");
        decoder = new org.slim3.datastore.json.Default();
        m.setTag(decoder.decode(reader, m.getTag()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        return m;
    }
}