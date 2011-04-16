package com.wordpong.api.meta;

//@javax.annotation.Generated(value = { "slim3-gen", "@VERSION@" }, date = "2011-04-16 14:11:46")
/** */
public final class AnswerMeta extends org.slim3.datastore.ModelMeta<com.wordpong.api.model.Answer> {

    /** */
    public final org.slim3.datastore.StringCollectionAttributeMeta<com.wordpong.api.model.Answer, java.util.List<java.lang.String>> answers = new org.slim3.datastore.StringCollectionAttributeMeta<com.wordpong.api.model.Answer, java.util.List<java.lang.String>>(this, "answers", "answers", java.util.List.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key> key = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key>(this, "__key__", "key", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.UnindexedAttributeMeta<com.wordpong.api.model.Answer, java.util.Locale> locale = new org.slim3.datastore.UnindexedAttributeMeta<com.wordpong.api.model.Answer, java.util.Locale>(this, "locale", "locale", java.util.Locale.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key> user = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, com.google.appengine.api.datastore.Key>(this, "user", "user", com.google.appengine.api.datastore.Key.class);

    /** */
    public final org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, java.lang.Long> version = new org.slim3.datastore.CoreAttributeMeta<com.wordpong.api.model.Answer, java.lang.Long>(this, "version", "version", java.lang.Long.class);

    private static final AnswerMeta slim3_singleton = new AnswerMeta();

    /**
     * @return the singleton
     */
    public static AnswerMeta get() {
       return slim3_singleton;
    }

    /** */
    public AnswerMeta() {
        super("Answer", com.wordpong.api.model.Answer.class);
    }

    @Override
    public com.wordpong.api.model.Answer entityToModel(com.google.appengine.api.datastore.Entity entity) {
        com.wordpong.api.model.Answer model = new com.wordpong.api.model.Answer();
        model.setAnswers(toList(java.lang.String.class, entity.getProperty("answers")));
        model.setKey(entity.getKey());
        java.util.Locale _locale = blobToSerializable((com.google.appengine.api.datastore.Blob) entity.getProperty("locale"));
        model.setLocale(_locale);
        model.setUser((com.google.appengine.api.datastore.Key) entity.getProperty("user"));
        model.setVersion((java.lang.Long) entity.getProperty("version"));
        return model;
    }

    @Override
    public com.google.appengine.api.datastore.Entity modelToEntity(java.lang.Object model) {
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        com.google.appengine.api.datastore.Entity entity = null;
        if (m.getKey() != null) {
            entity = new com.google.appengine.api.datastore.Entity(m.getKey());
        } else {
            entity = new com.google.appengine.api.datastore.Entity(kind);
        }
        entity.setProperty("answers", m.getAnswers());
        entity.setUnindexedProperty("locale", serializableToBlob(m.getLocale()));
        entity.setProperty("user", m.getUser());
        entity.setProperty("version", m.getVersion());
        entity.setProperty("slim3.schemaVersion", 1);
        return entity;
    }

    @Override
    protected com.google.appengine.api.datastore.Key getKey(Object model) {
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        return m.getKey();
    }

    @Override
    protected void setKey(Object model, com.google.appengine.api.datastore.Key key) {
        validateKey(key);
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        m.setKey(key);
    }

    @Override
    protected long getVersion(Object model) {
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        return m.getVersion() != null ? m.getVersion().longValue() : 0L;
    }

    @Override
    protected void assignKeyToModelRefIfNecessary(com.google.appengine.api.datastore.AsyncDatastoreService ds, java.lang.Object model) {
    }

    @Override
    protected void incrementVersion(Object model) {
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
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
        com.wordpong.api.model.Answer m = (com.wordpong.api.model.Answer) model;
        writer.beginObject();
        org.slim3.datastore.json.JsonCoder encoder = null;
        if(m.getAnswers() != null){
            writer.setNextPropertyName("answers");
            encoder = new org.slim3.datastore.json.Default();
            writer.beginArray();
            for(java.lang.String v : m.getAnswers()){
                encoder.encode(writer, v);
            }
            writer.endArray();
        }
        if(m.getKey() != null){
            writer.setNextPropertyName("key");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getKey());
        }
        if(m.getLocale() != null){
            writer.setNextPropertyName("locale");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getLocale());
        }
        if(m.getUser() != null){
            writer.setNextPropertyName("user");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getUser());
        }
        if(m.getVersion() != null){
            writer.setNextPropertyName("version");
            encoder = new org.slim3.datastore.json.Default();
            encoder.encode(writer, m.getVersion());
        }
        writer.endObject();
    }

    @Override
    protected com.wordpong.api.model.Answer jsonToModel(org.slim3.datastore.json.JsonRootReader rootReader, int maxDepth, int currentDepth) {
        com.wordpong.api.model.Answer m = new com.wordpong.api.model.Answer();
        org.slim3.datastore.json.JsonReader reader = null;
        org.slim3.datastore.json.JsonCoder decoder = null;
        reader = rootReader.newObjectReader("answers");
        decoder = new org.slim3.datastore.json.Default();
        {
            java.util.ArrayList<java.lang.String> elements = new java.util.ArrayList<java.lang.String>();
            org.slim3.datastore.json.JsonArrayReader r = rootReader.newArrayReader("answers");
            if(r != null){
                reader = r;
                int n = r.length();
                for(int i = 0; i < n; i++){
                    r.setIndex(i);
                    java.lang.String v = decoder.decode(reader, (java.lang.String)null)                    ;
                    if(v != null){
                        elements.add(v);
                    }
                }
                m.setAnswers(elements);
            }
        }
        reader = rootReader.newObjectReader("key");
        decoder = new org.slim3.datastore.json.Default();
        m.setKey(decoder.decode(reader, m.getKey()));
        reader = rootReader.newObjectReader("locale");
        decoder = new org.slim3.datastore.json.Default();
        m.setLocale(decoder.decode(reader, m.getLocale(), java.util.Locale.class));
        reader = rootReader.newObjectReader("user");
        decoder = new org.slim3.datastore.json.Default();
        m.setUser(decoder.decode(reader, m.getUser()));
        reader = rootReader.newObjectReader("version");
        decoder = new org.slim3.datastore.json.Default();
        m.setVersion(decoder.decode(reader, m.getVersion()));
        return m;
    }
}